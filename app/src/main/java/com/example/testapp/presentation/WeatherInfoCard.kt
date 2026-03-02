package com.example.testapp.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WeatherInfoCard(
    modifier: Modifier = Modifier
) {

    val timeText by rememberCurrentTimeText()
    var weatherText by remember { mutableStateOf("Yükleniyor...") }

    LaunchedEffect(Unit) {
        weatherText = try {
            val w = fetchIstanbulCurrentWeather()
            "İstanbul • ${w.tempC}°C • ${w.desc}"
        } catch (e: Exception) {
            "Hava alınamadı"
        }
    }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(Modifier.padding(10.dp)) {
            Text(text = timeText, color = Color.Black)
            Spacer(Modifier.height(4.dp))
            Text(text = weatherText, color = Color.Black)
        }
    }
}

@Composable
private fun rememberCurrentTimeText(): State<String> {
    return produceState(initialValue = "") {
        val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        while (true) {
            value = formatter.format(Date())
            delay(1000)
        }
    }
}

private data class WeatherNow(val tempC: Int, val desc: String)

private suspend fun fetchIstanbulCurrentWeather(): WeatherNow =
    withContext(Dispatchers.IO) {

        val url = URL("https://api.open-meteo.com/v1/forecast?latitude=41.0082&longitude=28.9784&current_weather=true&timezone=Europe%2FIstanbul")

        val conn = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
        }

        conn.inputStream.use { stream ->
            val jsonText = stream.bufferedReader().readText()
            val root = JSONObject(jsonText)
            val current = root.getJSONObject("current_weather")

            val temp = current.getDouble("temperature")
            val code = current.getInt("weathercode")

            WeatherNow(
                tempC = temp.toInt(),
                desc = weatherCodeToTr(code)
            )
        }
    }

private fun weatherCodeToTr(code: Int): String {
    return when (code) {
        0 -> "Açık"
        1, 2 -> "Az Bulutlu"
        3 -> "Bulutlu"
        61, 63, 65 -> "Yağmurlu"
        71, 73, 75 -> "Karlı"
        else -> "Bilinmiyor"
    }
}
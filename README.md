# 📱 Not Uygulaması

Jetpack Compose ile geliştirilmiş basit bir not alma uygulaması.

## 🛠 Kullanılan Teknolojiler
- Kotlin
- Jetpack Compose
- MVVM
- Room Database
- Navigation
- Coroutines
- Open-Meteo API

---

## 📸 Ekran Görüntüleri

### 🏠 Ana Sayfa

![Ana Sayfa](screenshots/Screenshot_20260302_143517.png)

**Özellikler:**
- "Notlara Git" butonu ile not listesine geçiş
- Sağ altta Not Ekle (FAB) butonu
- Hava Durumu ve Saat bilgisi gösterimi

---

### ➕ Not Ekleme

![Not Ekle](screenshots/Screenshot_20260302_143620.png)

**Özellikler:**
- Yeni not başlığı ve açıklama girişi
- Sağ altta kaydet işlemi (FAB)
- Hava Durumu ve Saat bilgisi gösterimi

---

### 📄 Notlar Kısmı

![Not Detay](screenshots/Screenshot_20260302_143637.png)

**Özellikler:**
- Kayıtlı notların listelenmesi
- Her nota tıklanıldığında detay/düzenleme ekranına geçiş

---

### ✏️ Not Düzenleme Kısmı

![Not düzenleme](screenshots/Screenshot_20260302_143656.png)

**Özellikler:**
- Notu güncelleme (Edit Mode)
- Sağ altta düzenleme/kaydet modu açma
- Not silme işlemi (AlertDialog ile)
- Room (Update & Delete işlemleri)
package com.example.testapp.presentation.navigation

sealed class Routes(val route: String) {
    data object Home : Routes("home")
    data object Add : Routes("add")
    data object Notes : Routes("notes")
    data object Detail : Routes("detail/{noteId}") {
        fun create(noteId: Int) = "detail/$noteId"
    }
}
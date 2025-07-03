package com.example.examen2doparcial.navigation

sealed class Screen(val route: String) {
    object Form    : Screen("form")
    object Exam    : Screen("exam")
    object Results : Screen("results")
    object Zodiac  : Screen("zodiac")
}

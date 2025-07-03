package com.example.examen2doparcial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.examen2doparcial.Interfaces.FormScreen
import com.example.examen2doparcial.Interfaces.ExamScreen
import com.example.examen2doparcial.navigation.Screen
import com.example.examen2doparcial.ui.theme.Examen2doParcialTheme
import com.example.examen2doparcial.ViewModel.SharedViewModel
import com.example.examen2doparcial.interfaces.ResultsScreen

class MainActivity : ComponentActivity() {
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Examen2doParcialTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Form.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Form.route) {
                            FormScreen(navController, sharedViewModel)
                        }
                        composable(Screen.Exam.route) {
                            ExamScreen(navController, sharedViewModel)
                        }
                        composable(Screen.Results.route) {
                            ResultsScreen(sharedViewModel)
                        }
                    }
                }
            }
        }
    }
}

package com.example.examen2doparcial.Interfaces

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.examen2doparcial.navigation.Screen
import com.example.examen2doparcial.ViewModel.SharedViewModel


// Modelo para una pregunta de examen
private data class Question(
    val text: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamScreen(
    navController: NavHostController,
    viewModel: SharedViewModel
) {
    val questions = listOf(
        Question(
            text = "¿Cuál es la suma de 2 + 2?",
            options = listOf("8", "6", "4", "3"),
            correctAnswerIndex = 2
        ),
        Question(
            text = "¿Cuál es la capital de México?",
            options = listOf("Monterrey", "Guadalajara", "Ciudad de México", "Puebla"),
            correctAnswerIndex = 2
        ),
        Question(
            text = "¿En qué año llegó el hombre a la Luna?",
            options = listOf("1965", "1969", "1972", "1975"),
            correctAnswerIndex = 1
        ),
        Question(
            text = "¿Cuál es el elemento químico con símbolo O?",
            options = listOf("Oro", "Osmio", "Oxígeno", "Orgullo"),
            correctAnswerIndex = 2
        ),
        Question(
            text = "¿Quién pintó la Mona Lisa?",
            options = listOf("Picasso", "Van Gogh", "Rembrandt", "Da Vinci"),
            correctAnswerIndex = 3
        ),
        Question(
            text = "¿Qué idioma se habla en Brasil?",
            options = listOf("Español", "Portugués", "Inglés", "Francés"),
            correctAnswerIndex = 1
        )
    )
    val selectedAnswers = remember { mutableStateListOf<Int?>().apply { repeat(questions.size) { add(null) } } }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Examen") })
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            items(questions.size) { index ->
                val question = questions[index]
                Text(text = "${index + 1}. ${question.text}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                question.options.forEachIndexed { optIndex, option ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (selectedAnswers[index] == optIndex),
                                onClick = { selectedAnswers[index] = optIndex }
                            )
                            .padding(8.dp)
                    ) {
                        RadioButton(
                            selected = (selectedAnswers[index] == optIndex),
                            onClick = { selectedAnswers[index] = optIndex }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = option)
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        // Calcular calificación y guardarla en ViewModel
                        val correctCount = selectedAnswers.mapIndexed { idx, sel ->
                            if (sel == questions[idx].correctAnswerIndex) 1 else 0
                        }.sum()
                        viewModel.updateScore(correctCount)
                        navController.navigate(Screen.Results.route)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Terminar")
                }
            }
        }
    }
}

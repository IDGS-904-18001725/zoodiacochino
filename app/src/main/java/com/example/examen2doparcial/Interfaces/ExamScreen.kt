package com.example.examen2doparcial.Interfaces

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.examen2doparcial.R
import com.example.examen2doparcial.navigation.Screen
import com.example.examen2doparcial.ViewModel.SharedViewModel

// Modelo para una pregunta de examen
private data class Question(
    val text: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)

// Función para cargar preguntas desde res/raw/questions.txt
private fun loadQuestionsFromRaw(context: Context): List<Question> {
    val input = context.resources.openRawResource(R.raw.questions)
    return input.bufferedReader().useLines { lines ->
        lines.mapNotNull { line ->
            val parts = line.split("|").map { it.trim() }
            if (parts.size == 3) {
                val text = parts[0]
                val options = parts[1].split(";").map { it.trim() }
                val correct = parts[2].toIntOrNull() ?: 0
                Question(text, options, correct)
            } else null
        }.toList()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamScreen(
    navController: NavHostController,
    viewModel: SharedViewModel
) {
    // 1. Cargar preguntas desde raw
    val context = LocalContext.current
    val questions by remember { mutableStateOf(loadQuestionsFromRaw(context)) }

    // 2. Estados de respuestas seleccionadas
    val selectedAnswers = remember(questions) {
        mutableStateListOf<Int?>().apply { repeat(questions.size) { add(null) } }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text("Examen") }) }
    ) { padding ->
        if (questions.isEmpty()) {
            // Indicador mientras carga
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                contentPadding = padding,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                items(questions.size) { index ->
                    val q = questions[index]
                    Text(
                        text = "${index + 1}. ${q.text}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.height(8.dp))
                    q.options.forEachIndexed { optIdx, option ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = (selectedAnswers[index] == optIdx),
                                    onClick = { selectedAnswers[index] = optIdx }
                                )
                                .padding(8.dp)
                        ) {
                            RadioButton(
                                selected = (selectedAnswers[index] == optIdx),
                                onClick = { selectedAnswers[index] = optIdx }
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(option)
                        }
                    }
                }
                item {
                    Spacer(Modifier.height(24.dp))
                    Button(
                        onClick = {
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
}

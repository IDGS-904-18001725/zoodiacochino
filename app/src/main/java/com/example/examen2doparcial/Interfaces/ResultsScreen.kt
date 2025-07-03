package com.example.examen2doparcial.interfaces

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.examen2doparcial.ViewModel.SharedViewModel

@Composable
fun ResultsScreen(viewModel: SharedViewModel) {
    // Leer datos desde el ViewModel
    val nombre    = viewModel.nombre
    val apellidos = viewModel.apellidos
    val dia       = viewModel.dia
    val mes       = viewModel.mes
    val anio      = viewModel.anio
    val score     = viewModel.score

    // Nombre completo
    val fullName = "$nombre $apellidos"

    // Calcular edad (año actual menos año de nacimiento)
    val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
    val age = currentYear - anio

    // Determinar signo zodiacal chino
    val zodiacs = listOf(
        "Mono", "Gallo", "Perro", "Cerdo", "Rata", "Buey",
        "Tigre", "Conejo", "Dragón", "Serpiente", "Caballo", "Cabra"
    )
    // Ciclo inicia en 1900 con Rata
    val index = ((anio - 1900) % 12 + 12) % 12
    val zodiac = zodiacs[index]

    // Cargar imagen desde res/drawable/zodiac_<minusculas>.png
    val context = LocalContext.current
    val imageName = "zodiac_${zodiac.lowercase()}"
    val imageResId = context.resources.getIdentifier(imageName, "drawable", context.packageName)

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Hola $fullName", style = MaterialTheme.typography.headlineSmall)
            Text(
                text = "Tienes $age años y tu signo zodiacal chino es $zodiac",
                style = MaterialTheme.typography.bodyLarge
            )
            val imageName = "zodiac_${zodiac.lowercase()}"
            val imageResId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
            if (imageResId != 0) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = "Signo zodiacal $zodiac",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
            Text(text = "Calificación: $score", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

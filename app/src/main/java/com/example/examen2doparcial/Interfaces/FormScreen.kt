package com.example.examen2doparcial.Interfaces

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.examen2doparcial.navigation.Screen
import com.example.examen2doparcial.ViewModel.SharedViewModel

@Composable
fun FormScreen(
    navController: NavHostController,
    viewModel: SharedViewModel
) {
    // Estados de los campos de texto
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var grupo by remember { mutableStateOf("") }
    var dia by remember { mutableStateOf("") }
    var mes by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }

    // Selección de sexo
    val sexos = listOf("Masculino", "Femenino")
    var sexoSelected by remember { mutableStateOf(sexos.first()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = apellidos,
            onValueChange = { apellidos = it },
            label = { Text("Apellidos") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = grupo,
            onValueChange = { grupo = it },
            label = { Text("Grupo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        // Fecha de nacimiento
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = dia,
                onValueChange = { dia = it.filter { char -> char.isDigit() } },
                label = { Text("Día") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = mes,
                onValueChange = { mes = it.filter { char -> char.isDigit() } },
                label = { Text("Mes") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = anio,
                onValueChange = { anio = it.filter { char -> char.isDigit() } },
                label = { Text("Año") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }
        // RadioButtons para sexo
        Text(text = "Sexo:")
        sexos.forEach { sexo ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = sexoSelected == sexo,
                    onClick = { sexoSelected = sexo }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = sexo)
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        // Botones
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                // Resetear campos
                nombre = ""; apellidos = ""; grupo = ""
                dia = ""; mes = ""; anio = ""
                sexoSelected = sexos.first()
            }) {
                Text("Limpiar")
            }
            Button(onClick = {
                // Guardar datos en ViewModel
                viewModel.setFormData(
                    nombre = nombre,
                    apellidos = apellidos,
                    grupo = grupo,
                    dia = dia.toIntOrNull() ?: 0,
                    mes = mes.toIntOrNull() ?: 0,
                    anio = anio.toIntOrNull() ?: 0,
                    sexo = sexoSelected
                )
                // Navegar a la pantalla de examen
                navController.navigate(Screen.Exam.route)
            }) {
                Text("Siguiente")
            }
        }
    }
}

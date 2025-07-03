package com.example.examen2doparcial.ViewModel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

class SharedViewModel : ViewModel() {
    // Datos del formulario
    var nombre by mutableStateOf("")
    var apellidos by mutableStateOf("")
    var grupo by mutableStateOf("")
    var dia by mutableIntStateOf(0)
    var mes by mutableIntStateOf(0)
    var anio by mutableIntStateOf(0)
    var sexo by mutableStateOf("Masculino")

    // Resultado del examen
    var score by mutableIntStateOf(0)

    /**
     * Guarda los datos del formulario.
     */
    fun setFormData(
        nombre: String,
        apellidos: String,
        grupo: String,
        dia: Int,
        mes: Int,
        anio: Int,
        sexo: String
    ) {
        this.nombre = nombre
        this.apellidos = apellidos
        this.grupo = grupo
        this.dia = dia
        this.mes = mes
        this.anio = anio
        this.sexo = sexo
    }

    /**
     * Guarda la calificación del examen.
     */
    fun updateScore(score: Int) {
        this.score = score
    }
}
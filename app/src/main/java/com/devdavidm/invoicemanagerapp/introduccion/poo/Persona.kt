package com.devdavidm.invoicemanagerapp.introduccion.poo

class Persona(var nombre: String="", var edad: Int, var estatura: Double, var sexo: Boolean) {
    private val planetaDeOrigen = "Tierra"
    fun printValues(){
        println()
        println("Planeta: ${this.planetaDeOrigen}")
        println("Nombre: ${this.nombre}")
        println("Edad: ${this.edad}")
        println("Estatura: ${this.estatura}")
        println("Sexo: ${this.sexo}")
    }

}
package com.devdavidm.invoicemanagerapp.introduccion.poo.taller1y2

// Función para obtener el resumen de notificaciones
fun getNotificacionesResumen(numeroNotificaciones: Int): String {
    return when (numeroNotificaciones) {
        0 -> "No hay mensajes disponibles"
        in 1..99 -> "Tienes $numeroNotificaciones notificaciones"
        else -> "Tienes 99+ notificaciones"
    }
}

// Función principal
fun main() {
    // Simulamos la cantidad de notificaciones
    val numeroNotificaciones = 100

    // Obtenemos el resumen de notificaciones
    val resumen = getNotificacionesResumen(numeroNotificaciones)

    // Imprimimos el resumen
    println(resumen)
}

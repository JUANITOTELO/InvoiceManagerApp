package com.devdavidm.invoicemanagerapp.introduccion.poo.taller1y2

// Función para simular una subasta
fun subasta(articulo: String, precioBase: Int, ofertas: List<Oferta>): String {
    // Obtener la oferta más alta
    val ofertaMasAlta = ofertas.maxByOrNull { it.precio }

    // Si no hay ofertas, vender el artículo a la casa de subastas
    if (ofertaMasAlta == null) {
        return "El artículo '$articulo' se ha vendido a la casa de subastas por el precio mínimo de $precioBase."
    }

    // Si hay una oferta, vender el artículo al mejor postor
    return "El artículo '$articulo' se ha vendido a ${ofertaMasAlta.nombre} por un precio de ${ofertaMasAlta.precio}."
}

// Función para crear una oferta
fun crearOferta(nombre: String, precio: Int): Oferta {
    return Oferta(nombre, precio)
}

// Clase para representar una oferta
data class Oferta(
    val nombre: String,
    val precio: Int
)

// Función principal
fun main() {
    // Lista de artículos a subastar
    val articulos = listOf("Cuadro", "Escultura", "Jarrón")

    // Lista de ofertas para cada artículo
    val ofertas = listOf(
        crearOferta("Juan", 1000),
        crearOferta("María", 1200),
        crearOferta("Pedro", 1500),
    )

    // Simular la subasta para cada artículo
    for (articulo in articulos) {
        val resultadoSubasta = subasta(articulo, 500, ofertas)
        println(resultadoSubasta)
    }
}

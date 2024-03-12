package com.devdavidm.invoicemanagerapp.introduccion.poo.taller1y2

// Clase para representar un plato del menú
data class Plato(
    var id: Int,
    var categoria: Categoria,
    var nombre: String,
    var descripcion: String,
    var precio: Double
)

// Enumeración para las categorías del menú
enum class Categoria {
    ENTRADAS,
    PLATOS_FUERTES,
    POSTRES,
    BEBIDAS
}

// Función para mostrar el menú
fun mostrarMenu(platos: List<Plato>) {
    for (categoria in Categoria.values()) {
        println("**$categoria**")
        for (plato in platos.filter { it.categoria == categoria }) {
            println("    ${plato.id} - ${plato.nombre} - ${plato.descripcion} - $${plato.precio}")
        }
    }
}

// Función para agregar un plato al menú
fun agregarPlato(platos: MutableList<Plato>, categoria: Categoria, nombre: String, descripcion: String, precio: Double): Int {
    val nuevoId = platos.size + 1
    val nuevoPlato = Plato(nuevoId, categoria, nombre, descripcion, precio)
    platos.add(nuevoPlato)
    return nuevoId
}

// Función para buscar un plato por su código
fun buscarPlatoPorCodigo(platos: List<Plato>, codigo: Int): Plato? {
    return platos.find { it.id == codigo }
}

// Función para modificar un plato
fun modificarPlato(plato: Plato, nombre: String, descripcion: String, precio: Double) {
    plato.nombre = nombre
    plato.descripcion = descripcion
    plato.precio = precio
}

// Función para eliminar un plato del menú
fun eliminarPlato(platos: MutableList<Plato>, codigo: Int) {
    platos.removeIf { it.id == codigo }
}

// Función principal
fun main() {
    // Lista de platos del menú
    val platos = mutableListOf<Plato>()

    // Agregar algunos platos al menú
    agregarPlato(platos, Categoria.ENTRADAS, "Ensalada", "Ensalada verde con tomates, pepinos y aderezo de vinagreta", 5.0)
    agregarPlato(platos, Categoria.PLATOS_FUERTES, "Pasta", "Pasta con salsa de tomate y carne molida", 10.0)
    agregarPlato(platos, Categoria.POSTRES, "Helado", "Helado de vainilla con chocolate caliente", 7.0)
    agregarPlato(platos, Categoria.BEBIDAS, "Agua", "Agua mineral natural", 2.0)

    // Mostrar el menú
    mostrarMenu(platos)

    // Buscar un plato por su código
    val plato = buscarPlatoPorCodigo(platos, 2)
    if (plato != null) {
        println("Plato encontrado: ${plato.nombre}")
    } else {
        println("No se encontró el plato con el código 2")
    }

    // Modificar un plato
    modificarPlato(plato!!, "Pasta con salsa boloñesa", "Pasta con salsa de tomate, carne molida y verduras", 12.0)

    // Eliminar un plato del menú
    eliminarPlato(platos, 3)

    // Mostrar el menú nuevamente
    mostrarMenu(platos)
}

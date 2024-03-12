package com.devdavidm.invoicemanagerapp.introduccion.poo.taller1y2

// Clase para representar un álbum de música
class Album(
    val nombre: String,
    val genero: String,
    val canciones: List<Cancion>
) {

    // Función para obtener la cantidad de canciones del álbum
    fun getCantidadCanciones(): Int {
        return canciones.size
    }

    // Función para obtener la canción más popular del álbum
    fun getCancionMasPopular(): Cancion? {
        var cancionMasPopular: Cancion? = null
        var maxReproducciones = 0

        for (cancion in canciones) {
            if (cancion.reproducciones > maxReproducciones) {
                maxReproducciones = cancion.reproducciones
                cancionMasPopular = cancion
            }
        }

        return cancionMasPopular
    }

    // Función para obtener las canciones populares del álbum
    fun getCancionesPopulares(): List<Cancion> {
        val cancionesPopulares = mutableListOf<Cancion>()

        for (cancion in canciones) {
            if (cancion.reproducciones >= 1000) {
                cancionesPopulares.add(cancion)
            }
        }

        return cancionesPopulares
    }

    // Función para imprimir la información de todas las canciones del álbum
    fun imprimirTodasLasCanciones() {
        for (cancion in canciones) {
            println(
                """
                ${cancion.titulo}, interpretada por ${cancion.artista}, se lanzó en ${cancion.anioLanzamiento}, ${cancion.reproducciones} reproducciones.
                """.trimIndent()
            )
        }
    }
}

// Clase para representar una canción
class Cancion(
    val titulo: String,
    val artista: String,
    val anioLanzamiento: Int,
    val reproducciones: Int
)

// Función para probar la clase Álbum
fun main() {
    val canciones = listOf(
        Cancion("Canción 1", "Artista 1", 2023, 1200),
        Cancion("Canción 2", "Artista 2", 2022, 500),
        Cancion("Canción 3", "Artista 3", 2021, 800),
        Cancion("Canción 4", "Artista 4", 2020, 1500),
    )

    val album = Album("Mi álbum", "Pop", canciones)

    // Imprimir la cantidad de canciones del álbum
    println("Cantidad de canciones: ${album.getCantidadCanciones()}")

    // Imprimir la canción más popular del álbum
    val cancionMasPopular = album.getCancionMasPopular()
    if (cancionMasPopular != null) {
        println("Canción más popular: ${cancionMasPopular.titulo}")
    } else {
        println("No hay canciones populares en este álbum")
    }

    // Imprimir las canciones populares del álbum
    val cancionesPopulares = album.getCancionesPopulares()
    println("Canciones populares:")
    for (cancion in cancionesPopulares) {
        println(cancion.titulo)
    }

    // Imprimir la información de todas las canciones del álbum
    println("Todas las canciones:")
    album.imprimirTodasLasCanciones()
}

package com.devdavidm.invoicemanagerapp.introduccion.poo.taller1y2

class Nequi {

    private var saldo: Double = 4500.0
    private var intentosAcceso = 3

    fun iniciarSesion(): Boolean {
        var accesoCorrecto = false
        while (intentosAcceso > 0 && !accesoCorrecto) {
            print("Ingrese su número de celular: ")
            val celular = readLine()!!.trim()

            print("Ingrese su clave de 4 dígitos: ")
            val clave = readLine()!!.trim().toIntOrNull()

            if (celular == "1234567890" && clave == 1234) {
                accesoCorrecto = true
                println("¡Bienvenido a Nequi!")
            } else {
                intentosAcceso--
                println("¡Upps! Parece que tus datos de acceso no son correctos. Te quedan $intentosAcceso intentos más.")
            }
        }

        return accesoCorrecto
    }

    fun mostrarMenu() {
        while (true) {
            println("---- Nequi ----")
            println("Saldo disponible: $$saldo")
            println("1. Sacar dinero")
            println("2. Enviar dinero")
            println("3. Recargar saldo")
            println("4. Salir")

            print("Seleccione una opción: ")
            val opcion = readLine()!!.trim().toIntOrNull()

            if (opcion != null && opcion in 1..4) {
                when (opcion) {
                    1 -> sacarDinero()
                    2 -> enviarDinero()
                    3 -> recargarSaldo()
                    4 -> {
                        println("¡Gracias por usar Nequi!")
                        break
                    }
                }
            } else {
                println("Opción inválida. Intente de nuevo.")
            }
        }
    }

    private fun sacarDinero() {
        println("¿Cómo desea retirar dinero?")
        println("1. Cajero automático")
        println("2. Punto físico Nequi")

        print("Seleccione una opción: ")
        val opcionRetiro = readLine()!!.trim().toIntOrNull()

        if (opcionRetiro != null && opcionRetiro in 1..2) {
            if (saldo < 2000.0) {
                println("No tienes saldo suficiente para realizar un retiro.")
            } else {
                print("¿Cuánto desea retirar? ")
                val montoRetiro = readLine()!!.trim().toDoubleOrNull()

                if (montoRetiro != null && montoRetiro > 0.0 && montoRetiro <= saldo) {
                    saldo -= montoRetiro
                    println("Se ha generado un código de 6 dígitos para el retiro. Por favor, diríjase al cajero automático o punto Nequi más cercano.")
                    println("Código de retiro: ${(100000..999999).random()}")
                    println("Saldo disponible: $$saldo")
                } else {
                    println("Monto inválido. Intente de nuevo.")
                }
            }
        } else {
            println("Opción inválida. Intente de nuevo.")
        }
    }

    private fun enviarDinero() {
        print("Ingrese el número de celular a enviar: ")
        val celularDestino = readLine()!!.trim()

        print("Ingrese el valor a enviar: ")
        val montoEnvio = readLine()!!.trim().toDoubleOrNull()

        if (montoEnvio != null && montoEnvio > 0.0 && montoEnvio <= saldo) {
            saldo -= montoEnvio
            println("Se ha enviado $montoEnvio a $celularDestino.")
            println("Saldo disponible: $$saldo")
        } else {
            println("Monto inválido o saldo insuficiente. Intente de nuevo.")
        }
    }

    private fun recargarSaldo() {
        print("Ingrese el valor a recargar: ")
        val montoRecarga = readLine()!!.trim().toDoubleOrNull()

        if (montoRecarga != null && montoRecarga > 0.0) {
            println("¿Desea realizar una recarga de $montoRecarga a su saldo? (y/n)")
            val confirmacion = readLine()!!.trim().lowercase()

            if (confirmacion == "y") {
                saldo += montoRecarga
                println("Se ha realizado una recarga de $montoRecarga a su saldo.")
                println("Saldo disponible: $$saldo")
            } else {
                println("Recarga cancelada.")
            }
        } else {
            println("Monto inválido. Intente de nuevo.")
        }
    }
}

fun main() {
    val nequi = Nequi()

    if (nequi.iniciarSesion()) {
        nequi.mostrarMenu()
    } else {
        println("¡Acceso denegado! Intente de nuevo más tarde.")
    }
}


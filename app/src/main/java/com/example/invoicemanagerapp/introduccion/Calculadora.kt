package com.example.invoicemanagerapp.introduccion

import kotlin.math.pow

class Calculadora {
    fun sumar(a:Int,b:Int):Int{
        return a+b;
    }
    fun restar(a:Int,b:Int):Int{
        return a-b;
    }
    fun multiplicar(a:Int,b:Int):Int{
        return a*b;
    }
    fun dividir(a:Int,b:Int):Int{
        return a/b;
    }
    fun potencia(a:Int,b:Int):Int{
        return (a.toDouble().pow(b.toDouble())).toInt();
    }
    fun raizCuadrada(a:Int):Double{
        return(a.toDouble().pow(0.5));
    }
}
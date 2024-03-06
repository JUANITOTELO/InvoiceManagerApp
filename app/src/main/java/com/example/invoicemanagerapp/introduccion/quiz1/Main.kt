package com.example.invoicemanagerapp.introduccion.quiz1
import com.example.invoicemanagerapp.introduccion.Calculadora

fun main() {
    println("Ingrese a y b: ")
    val a:Int = readln().toInt();
    println("a: $a");
    val b:Int = readln().toInt();
    println("b: $b");
    print("\nOpciones:\n sumar\n restar\n multiplicar\n dividir\n potenciar\n raíz\n\n");
    val opcion:String = readln().toString().lowercase();
    val c = Calculadora();
    when(opcion){
        "sumar"->println("Suma: "+c.sumar(a,b));
        "restar"->println("Resta: "+c.restar(a,b));
        "multiplicar"->println("Multiplicación: "+c.multiplicar(a,b));
        "dividir"->println("Dividición: "+c.dividir(a,b));
        "pontenciar"->println("Potenciación: "+c.potencia(a,b));
        "raiz"->println("Raíz cuadrada de a: "+c.raizCuadrada(a)+"\nRaíz cuadrada de b: "+c.raizCuadrada(b));
        else->println("Opción incorrecta.");
    }
}

package com.imcapp.logica;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Calculadora {
    // Atributos
    private static List<MedicionIMC> historial = new ArrayList<>();

    // Método para calcular el IMC
    public static String calcularIMC(double peso, double altura) {
        double imc = peso / (altura * altura);
        return determinarEstadoIMC(imc);
    }

    // Método para determinar el estado del IMC
    static String determinarEstadoIMC(double imc) {
        if (imc < 18.5) {
            return "Bajo peso";
        } else if (imc < 25) {
            return "Peso normal";
        } else {
            return "Sobrepeso";
        }
    }

    // Método para determinar el color del IMC
    public static Color determinarColorIMC(double peso, double altura) {
        double imc = peso / (altura * altura);

        if (imc < 18.5) {
            return Color.RED;
        } else if (imc < 24.9) {
            return Color.GREEN;
        } else {
            return Color.YELLOW;
        }
    }

    // Método para guardar una medición
    public static void guardarMedicion(String nombre, String edad, double peso, double altura) {
        MedicionIMC medicion = new MedicionIMC(nombre, edad, peso, altura);
        historial.add(medicion);
    }

    // Método para obtener el historial
    public static List<MedicionIMC> obtenerHistorial() {
        return historial;
    }
}

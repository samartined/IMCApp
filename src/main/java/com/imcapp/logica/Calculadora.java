package com.imcapp.logica;

import java.awt.Color;

public class Calculadora {
    public static String calcularIMC(double peso, double altura) {
        double imc = peso / (altura * altura);
        return determinarEstadoIMC(imc);
    }

    private static String determinarEstadoIMC(double imc) {
        if (imc < 18.5) {
            return "Bajo peso";
        } else if (imc < 25) {
            return "Peso normal";
        } else {
            return "Sobrepeso";
        }
    }

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
}

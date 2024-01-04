package com.imcapp.logica;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Calculadora implements Serializable {

    // Constantes
    // Aviso: Este campo serialVersionUID se declara para garantizar la
    // compatibilidad de la serialización
    @SuppressWarnings("unused") // Suprimir advertencia de variable no utilizada
    private static final long SERIAL_VERSION_UID = 1L; // Numero de version para la serializacion

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

    // Método para guardar el historial en un archivo
    public static void guardarHistorial() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("historial.dat"))) { // Crear el flujo
                                                                                                       // de salida
            oos.writeObject(historial); // Escribir el objeto en el flujo

        } catch (IOException e) { // Manejamos IOException que es la excepción que puede lanzar FileOutputStream
            System.out.println("Error al guardar el historial.");
        }
    }

    // Método para cargar el historial desde un archivo
    @SuppressWarnings("unchecked") // Suprimir advertencia de unchecked cast.
    public static void cargarHistorial() {
        File file = new File("historial.dat"); // Crear el archivo
        if (file.exists()) { // Verificar si el archivo existe
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("historial.dat"))) {
                historial = (List<MedicionIMC>) ois.readObject(); // Leer el objeto del flujo

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
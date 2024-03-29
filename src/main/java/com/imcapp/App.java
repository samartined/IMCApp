package com.imcapp;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;

import com.imcapp.gui.guiprincipal.GUI;
import com.imcapp.logica.Calculadora;

public class App {
    // Método main
    public static void main(String[] args) {
        // Cargar el historial al inicio
        Calculadora.cargarHistorial();

        // Crear la interfaz
        SwingUtilities.invokeLater(() -> { // Ejecutar la interfaz
            GUI gui = new GUI(); // Crear la interfaz
            gui.setVisible(true); // Mostrar la interfaz

            // Agregar el evento para guardar el historial al cerrar la ventana
            gui.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    Calculadora.guardarHistorial();
                    System.exit(0); // Cerrar la aplicación
                }
            });
        });
    }
}
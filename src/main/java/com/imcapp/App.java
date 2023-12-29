package com.imcapp;

import javax.swing.SwingUtilities;

import com.imcapp.gui.GUI;

public class App {

    // Método main
    public static void main(String[] args) {
        // Crear la interfaz
        SwingUtilities.invokeLater(new Runnable() { // Crear un hilo para la interfaz
            @Override
            // Método para ejecutar la interfaz
            public void run() { // Ejecutar la interfaz
                new GUI().setVisible(true); // Crear la interfaz
            }
        });
    }
}
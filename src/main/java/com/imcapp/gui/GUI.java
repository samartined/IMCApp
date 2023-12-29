package com.imcapp.gui;

import javax.swing.*;

import com.imcapp.logica.Calculadora;
import com.imcapp.logica.MedicionIMC;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GUI extends JFrame {
    // Componentes de la interfaz
    private JTextField txtNombhre, txtEdad, txtPeso, txtAltura;
    private JLabel lblResultado;
    private JButton btnCalcular, btnGuardar, btnHistorial;

    // Constructor
    public GUI() {
        // Configuración de la interfaz
        setTitle("Calculadora de IMC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Abre la ventana en tamaño completo
        setLayout(new GridLayout(7, 2));

        add(new JLabel("Nombre:"));
        txtNombhre = new JTextField();
        add(txtNombhre);

        add(new JLabel("Edad:"));
        txtEdad = new JTextField();
        add(txtEdad);

        add(new JLabel("Peso:"));
        txtPeso = new JTextField();
        add(txtPeso);

        add(new JLabel("Altura:"));
        txtAltura = new JTextField();
        add(txtAltura);

        lblResultado = new JLabel("");
        add(lblResultado);

        // Botón para calcular el IMC
        btnCalcular = new JButton("Calcular IMC");
        add(btnCalcular);
        // Agregar el evento al botón
        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularIMC();
            }
        });

        // Botón para guardar los datos
        btnGuardar = new JButton("Guardar Datos");
        add(btnGuardar);
        // Agregar el evento al botón
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarDatos();
            }
        });

        // Botón para ver el historial
        btnHistorial = new JButton("Ver Historial");
        add(btnHistorial);
        // Agregar el evento al botón
        btnHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verHistorial();
            }
        });

    }

    private void calcularIMC() {
        try {
            double peso = Double.parseDouble(txtPeso.getText());
            double altura = Double.parseDouble(txtAltura.getText());
            if (peso <= 0 || altura <= 0)
                throw new ArithmeticException("Peso y altura deben ser mayores a cero.");

            String estadoIMC = Calculadora.calcularIMC(peso, altura);
            Color colorIMC = Calculadora.determinarColorIMC(peso, altura);

            mostrarResultado(estadoIMC, colorIMC);

        } catch (NumberFormatException e) {
            lblResultado.setText("Error: Ingrese valores numéricos para peso y altura.");
        } catch (ArithmeticException e) {
            lblResultado.setText("Error: " + e.getMessage());
        } catch (Exception e) {
            lblResultado.setText("Error: " + e.getMessage());
        }
    }

    private void mostrarResultado(String estadoIMC, Color colorIMC) {
        lblResultado.setText(estadoIMC);
        lblResultado.setForeground(colorIMC);
    }

    private void guardarDatos() {
        // Obtener los datos de los campos de texto
        String nombre = txtNombhre.getText();
        String edad = txtEdad.getText();

        // Validar que los campos no estén vacíos
        try {
            // Convertir los datos a los tipos de datos correctos
            double peso = Double.parseDouble(txtPeso.getText());
            double altura = Double.parseDouble(txtAltura.getText());

            // Guardar los datos en el historial
            Calculadora.guardarMedicion(nombre, edad, peso, altura);

            // Mostrar mensaje de éxito al guardar
            lblResultado.setText("Datos guardados con éxito para el usuario " + nombre + ".");

            // Mostrar mensaje de error si los datos no son válidos
        } catch (NumberFormatException e) {
            lblResultado.setText("Error: Ingrese valores numéricos para peso y altura.");
        }
    }

    // Método para mostrar el historial
    private void verHistorial() {
        // Obtener el historial
        List<MedicionIMC> historial = Calculadora.obtenerHistorial();
        // Crear la ventana del historial
        HistorialFrame historialFrame = new HistorialFrame(historial);
        historialFrame.setLocationRelativeTo(null); // Centrar la ventana
        historialFrame.setVisible(true); // Mostrar la ventana
    }
}
package com.imcapp.gui;

import javax.swing.*;

import com.imcapp.logica.Calculadora;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    // Componentes de la interfaz
    private JTextField txtNombhre, txtEdad, txtPeso, txtAltura;
    private JLabel lblResultado;
    private JButton btnCalcular, btnGuardar, btnHistorial;

    // Constructor
    public GUI() {
        // Configuración de la interfaz
        setTitle("Calculadora de IMC");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        btnCalcular = new JButton("Calcular IMC");
        add(btnCalcular);

        btnGuardar = new JButton("Guardar Datos");
        add(btnGuardar);

        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularIMC();
            }
        });
    }

    private void calcularIMC() {
        try {
            double peso = Double.parseDouble(txtPeso.getText());
            double altura = Double.parseDouble(txtAltura.getText());

            String estadoIMC = Calculadora.calcularIMC(peso, altura);
            Color colorIMC = Calculadora.determinarColorIMC(peso, altura);

            mostrarResultado(estadoIMC, colorIMC);

        } catch (Exception e) {
            lblResultado.setText("Error: " + e.getMessage());
        }
    }

    private void mostrarResultado(String estadoIMC, Color colorIMC) {
        lblResultado.setText(estadoIMC);
        lblResultado.setForeground(colorIMC);
    }

    private void guardarDatos() {
        // TODO: Implementar
    }

    private void verHistorial() {
        // TODO: Implementar

    }

}
package com.imcapp.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.imcapp.logica.Calculadora;
import com.imcapp.logica.MedicionIMC;

public class GUI extends JFrame {
    private JTextField txtNombre, txtEdad, txtPeso, txtAltura;
    private JLabel lblResultado;
    private JButton btnCalcular, btnGuardar, btnHistorial;

    public GUI() {
        setTitle("Calculadora de IMC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new GridLayout(7, 2, 10, 10));
        getContentPane().setBackground(Color.WHITE);

        Font labelFont = new Font("Arial", Font.BOLD, 20);
        Font textFieldFont = new Font("Helvetica", Font.BOLD, 20);

        add(createLabeledTextField("Nombre:", labelFont, textFieldFont, "txtNombre"));
        add(createLabeledTextField("Edad:", labelFont, textFieldFont, "txtEdad"));
        add(createLabeledTextField("Peso (Kg):", labelFont, textFieldFont, "txtPeso"));
        add(createLabeledTextField("Altura (en metros):", labelFont, textFieldFont, "txtAltura"));

        lblResultado = createResultLabel();

        btnCalcular = createStyledButton("Calcular IMC", new Color(0, 128, 0), e -> calcularIMC());
        btnGuardar = createStyledButton("Guardar Datos", new Color(0, 0, 128), e -> guardarDatos());
        btnHistorial = createStyledButton("Ver Historial", new Color(64, 0, 64), e -> verHistorial());

        add(btnCalcular);
        add(lblResultado);
        add(btnGuardar);
        add(btnHistorial);
    }

    private JPanel createLabeledTextField(String labelText, Font labelFont, Font textFieldFont, String fieldName) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.ipady = 10;

        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);

        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JTextField textField = new JTextField();
        textField.setFont(textFieldFont);
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createEmptyBorder());

        panel.add(textField, gbc);

        switch (fieldName) {
            case "txtNombre":
                txtNombre = textField;
                break;
            case "txtEdad":
                txtEdad = textField;
                break;
            case "txtPeso":
                txtPeso = textField;
                break;
            case "txtAltura":
                txtAltura = textField;
                break;
            default:
                break;
        }

        return panel;
    }

    private JLabel createResultLabel() {
        JLabel resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return resultLabel;
    }

    private JButton createStyledButton(String text, Color color, ActionListener actionListener) {
        StyledButton button = new StyledButton(text, color);
        button.addActionListener(actionListener);
        return button;
    }

    private void calcularIMC() {
        try {
            if (txtPeso == null || txtAltura == null) {
                throw new Exception("Los campos de texto no están inicializados.");
            }

            double peso = Double.parseDouble(txtPeso.getText());
            double altura = Double.parseDouble(txtAltura.getText());

            if (peso <= 0 || altura <= 0) {
                throw new ArithmeticException("Peso y altura deben ser mayores a cero.");
            }

            double imc = peso / (altura * altura);
            String estadoIMC = Calculadora.calcularIMC(peso, altura);
            Color colorIMC = Calculadora.determinarColorIMC(peso, altura);

            mostrarResultado(estadoIMC, colorIMC, imc);

        } catch (NumberFormatException e) {
            lblResultado.setText("Error: Ingrese valores numéricos para peso y altura.");
        } catch (ArithmeticException e) {
            lblResultado.setText("Error: " + e.getMessage());
        } catch (Exception e) {
            lblResultado.setText("Error: " + e.getMessage());
        }
    }

    private void mostrarResultado(String estadoIMC, Color colorIMC, double imc) {
        String mensaje = String.format("%s (IMC: %.1f)", estadoIMC, imc);
        SwingUtilities.invokeLater(() -> {
            lblResultado.setText(mensaje);
            lblResultado.setForeground(colorIMC);
        });
    }

    private void guardarDatos() {
        try {
            String nombre = txtNombre.getText();
            String edad = txtEdad.getText();

            double peso = Double.parseDouble(txtPeso.getText());
            double altura = Double.parseDouble(txtAltura.getText());

            Calculadora.guardarMedicion(nombre, edad, peso, altura);
            lblResultado.setText("Datos guardados con éxito para el usuario " + nombre + ".");

        } catch (NumberFormatException e) {
            lblResultado.setText("Error: Ingrese valores numéricos para peso y altura.");
        }
    }

    private void verHistorial() {
        List<MedicionIMC> historial = Calculadora.obtenerHistorial();
        HistorialFrame historialFrame = new HistorialFrame(historial);
        historialFrame.setLocationRelativeTo(null);
        historialFrame.setVisible(true);
    }
}
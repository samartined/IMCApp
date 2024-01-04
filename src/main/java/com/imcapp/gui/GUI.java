package com.imcapp.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.imcapp.logica.Calculadora;
import com.imcapp.logica.MedicionIMC;

public class GUI extends JFrame {
    // Componentes de la interfaz
    private JTextField txtNombre, txtEdad, txtPeso, txtAltura;
    private JLabel lblResultado;
    private JButton btnCalcular, btnGuardar, btnHistorial;

    // Constructor
    public GUI() {
        // Configuración de la interfaz
        setTitle("Calculadora de IMC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Abre la ventana en tamaño completo
        setLayout(new GridLayout(7, 2, 10, 10));
        getContentPane().setBackground(Color.WHITE); // Color de fondo

        // Fuente para los componentes
        Font labelFont = new Font("Arial", Font.PLAIN, 18);
        Font textFieldFont = new Font("Helvetica", Font.BOLD, 16);

        add(createLabeledTextField("Nombre:", labelFont, textFieldFont));
        add(createLabeledTextField("Edad:", labelFont, textFieldFont));
        add(createLabeledTextField("Peso (Kg):", labelFont, textFieldFont));
        add(createLabeledTextField("Altura (en metros):", labelFont, textFieldFont));

        // Crear los campos de texto para ingresar los datos
        lblResultado = new JLabel("");
        lblResultado.setFont(new Font("Arial", Font.BOLD, 20)); // Fuente para el resultado
        lblResultado.setHorizontalAlignment(SwingConstants.CENTER); // Alinear el texto al centro

        // Botón para calcular el IMC
        btnCalcular = new StyledButton("Calcular IMC", new Color(0, 128, 0)); // Color verde
        btnCalcular.addActionListener(e -> calcularIMC()); // Agregar el evento para calcular el IMC

        // Botón para guardar los datos
        btnGuardar = new StyledButton("Guardar Datos", new Color(0, 0, 128)); // Color azul
        btnGuardar.addActionListener(e -> guardarDatos()); // Agregar el evento para guardar los datos

        // Botón para ver el historial
        btnHistorial = new StyledButton("Ver Historial", new Color(64, 0, 64)); // Color morado
        btnHistorial.addActionListener(e -> verHistorial()); // Agregar el evento para ver el historial

        // Añadimos los campos de texto y los botones a la ventana en el orden deseado
        add(btnCalcular);
        add(lblResultado);
        add(btnGuardar);
        add(btnHistorial);

    }

    // Método para crear un campo de texto con etiqueta
    private JPanel createLabeledTextField(String labelText, Font labelFont, Font textFieldFont) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 10, 0, 10); // Espacio a la izquierda y derecha del label
        gbc.ipady = 10; // Altura adicional para centrar verticalmente

        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);

        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Hace que el campo de texto ocupe todo el espacio horizontal disponible

        JTextField textField = new JTextField();
        textField.setFont(textFieldFont);
        textField.setOpaque(false); // Hacer el fondo transparente
        textField.setBorder(BorderFactory.createEmptyBorder()); // Eliminar el borde

        panel.add(textField, gbc);

        return panel;
    }

    // Método para calcular el IMC
    private void calcularIMC() {
        try {
            double peso = Double.parseDouble(txtPeso.getText());
            double altura = Double.parseDouble(txtAltura.getText());
            if (peso <= 0 || altura <= 0)
                throw new ArithmeticException("Peso y altura deben ser mayores a cero.");

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

    // Método para mostrar el resultado
    private void mostrarResultado(String estadoIMC, Color colorIMC, double imc) {
        String mensaje = String.format("%s (IMC: %.1f)", estadoIMC, imc);
        lblResultado.setText(mensaje);
        lblResultado.setForeground(colorIMC);
    }

    // Método para guardar los datos
    private void guardarDatos() {
        // Obtener los datos de los campos de texto
        String nombre = txtNombre.getText();
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
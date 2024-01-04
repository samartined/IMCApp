package com.imcapp.gui;

import javax.swing.*;

import com.imcapp.logica.Calculadora;
import com.imcapp.logica.MedicionIMC;

import java.awt.*;
import java.util.List;

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
        Font font = new Font("Arial", Font.PLAIN, 18);

        // Crear los campos de texto
        add(createLabeledTextField("Nombre:", font, SwingConstants.RIGHT, SwingConstants.LEFT));
        add(createLabeledTextField("Edad:", font, SwingConstants.RIGHT, SwingConstants.LEFT));
        add(createLabeledTextField("Peso:", font, SwingConstants.RIGHT, SwingConstants.LEFT));
        add(createLabeledTextField("Altura:", font, SwingConstants.RIGHT, SwingConstants.LEFT));

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
    private JPanel createLabeledTextField(String labelText, Font font, int labelHorizontalAlignment,
            int textFieldHorizontalAlignment) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        label.setFont(font);
        JTextField textField = new JTextField();
        textField.setFont(font);
        textField.setOpaque(false); // Hacer el fondo transparente
        textField.setBorder(BorderFactory.createEmptyBorder()); // Eliminar el borde
        panel.add(label, BorderLayout.WEST);
        panel.add(Box.createRigidArea(new Dimension(10, 0))); // Espacio adicional entre la etiqueta y el campo de texto
        panel.add(textField, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Añadir espacio entre componentes
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
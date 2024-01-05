package com.imcapp.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.imcapp.logica.Calculadora;
import com.imcapp.logica.MedicionIMC;

public class GUI extends JFrame {

    public static final String PANEL_PRINCIPAL = "panelPrincipal";
    public static final String PANEL_HISTORIAL = "panelHistorial";

    private CardLayout cardLayout;
    private JPanel cardPanel;

    private JTextField txtNombre, txtEdad, txtPeso, txtAltura;
    private JLabel lblResultado;
    private JButton btnCalcular, btnGuardar, btnHistorial, btnVolver;

    private HistorialFrame historialFrame;

    public GUI() {
        setTitle("Calculadora de IMC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        txtNombre = new JTextField();
        txtEdad = new JTextField();
        txtPeso = new JTextField();
        txtAltura = new JTextField();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(crearPanelPrincipal(), PANEL_PRINCIPAL);
        cardPanel.add(crearPanelHistorial(), PANEL_HISTORIAL);

        add(cardPanel, BorderLayout.CENTER);

        cardLayout.show(cardPanel, PANEL_PRINCIPAL);
    }

    private JPanel crearPanelPrincipal() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));

        panel.add(createLabeledTextField("Nombre:", txtNombre));
        panel.add(createLabeledTextField("Edad:", txtEdad));
        panel.add(createLabeledTextField("Peso (Kg):", txtPeso));
        panel.add(createLabeledTextField("Altura (en metros):", txtAltura));

        lblResultado = new JLabel("");
        lblResultado.setFont(new Font("Arial", Font.BOLD, 20));
        lblResultado.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblResultado);

        panel.add(crearPanelBotones());

        return panel;
    }

    private JPanel createLabeledTextField(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(label);

        textField.setFont(new Font("Helvetica", Font.BOLD, 20));
        textField.setOpaque(false);
        panel.add(textField);

        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 10, 10));

        panelBotones.add(crearBoton("Calcular IMC", e -> calcularIMC(), new Color(0, 128, 0)));
        panelBotones.add(crearBoton("Guardar Datos", e -> guardarDatos(), new Color(0, 0, 128)));
        panelBotones.add(crearBoton("Ver Historial", e -> mostrarVista(PANEL_HISTORIAL), new Color(64, 0, 64)));

        return panelBotones;
    }

    private JButton crearBoton(String texto, ActionListener actionListener, Color color) {
        JButton boton = new JButton(texto);
        boton.addActionListener(actionListener);
        boton.setFont(new Font("Arial", Font.BOLD, 20));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        return boton;
    }

    private JPanel crearPanelHistorial() {
        JPanel panel = new JPanel(new BorderLayout());

        panel.add(crearBoton("Volver", e -> mostrarVista(PANEL_PRINCIPAL), Color.GRAY), BorderLayout.SOUTH);

        panel.add(new JScrollPane(new HistorialFrame(Calculadora.obtenerHistorial()).obtenerTabla()), BorderLayout.CENTER);

        return panel;
    }

    private void mostrarVista(String nombrePanel) {
        cardLayout.show(cardPanel, nombrePanel);
    }

    // Método para calcular el IMC
    private void calcularIMC() {
        try {
            // Asegúrate de que los campos de texto estén creados antes de acceder a ellos
            if (txtPeso == null || txtAltura == null) {
                throw new Exception("Los campos de texto no están inicializados.");
            }

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
}
package com.imcapp.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class StyledButton extends JButton {

    public StyledButton(String text, Color color) {

        // Llamar al constructor de la clase padre
        super(text);

        // Establecer el estilo del botón
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
                (BorderFactory.createEmptyBorder(5, 10, 5, 10)))); // Establecer el borde del botón con un grosor de 2

        setContentAreaFilled(false);

        // Establecer el color de fondo y el color de primer plano
        setOpaque(true);

        // Establecer el color de fondo y el color de primer plano
        setBackground(color);

        // Establecer el color de primer plano
        setForeground(Color.WHITE);

        // Cambiar el tamaño de la fuente
        setFont(getFont().deriveFont(20f));
    }
}
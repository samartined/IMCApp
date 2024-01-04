package com.imcapp.logica;

import java.io.Serializable;

public class MedicionIMC implements Serializable {
    // Atributos
    private String nombre;
    private String edad;
    private double peso;
    private double altura;

    // Constructor
    public MedicionIMC(String nombre, String edad, double peso, double altura) {
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.altura = altura;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    // Método para obtener el IMC
    public double getIMC() {
        return peso / (altura * altura);
    }

    // Método para obtener el IMC redondeado a un decimal
    public double getIMCRedondeado() {
        return Math.round(getIMC() * 10) / 10.0;
    }

    // Método para obtener el estado del IMC
    public String getEstadoIMC() {
        return Calculadora.determinarEstadoIMC(getIMC());
    }
}
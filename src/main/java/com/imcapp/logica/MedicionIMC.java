package com.imcapp.logica;

public class MedicionIMC {
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

    public double getIMC() {
        return peso / (altura * altura);
    }

    public String getEstadoIMC() {
        return Calculadora.determinarEstadoIMC(getIMC());
    }

}

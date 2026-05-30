package com.alvaro.computadorita.Models;

public class ExpresionRequest {
    private String expresion;

    public ExpresionRequest() {}

    public ExpresionRequest(String expresion) {
        this.expresion = expresion;
    }

    public String getExpresion() { return expresion; }
    public void setExpresion(String expresion) { this.expresion = expresion; }
}

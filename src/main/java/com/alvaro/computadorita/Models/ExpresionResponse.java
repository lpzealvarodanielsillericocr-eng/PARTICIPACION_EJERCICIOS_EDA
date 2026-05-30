package com.alvaro.computadorita.Models;

public class ExpresionResponse {
    private String infix;
    private String postfix;
    private String resultado;
    private boolean exito;
    private String error;

    // Constructor éxito
    public ExpresionResponse(String infix, String postfix, String resultado) {
        this.infix = infix;
        this.postfix = postfix;
        this.resultado = resultado;
        this.exito = true;
        this.error = null;
    }

    // Constructor error
    public ExpresionResponse(String infix, String error) {
        this.infix = infix;
        this.postfix = null;
        this.resultado = null;
        this.exito = false;
        this.error = error;
    }

    public String getInfix() { return infix; }
    public String getPostfix() { return postfix; }
    public String getResultado() { return resultado; }
    public boolean isExito() { return exito; }
    public String getError() { return error; }
}

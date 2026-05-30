package com.miguel.computadorita.Tools;

import java.util.Stack;


public class Calculadora {

   
    public static String convertirAPostfix(String infix) {
        if (infix == null || infix.isBlank()) {
            throw new IllegalArgumentException("La expresión no puede estar vacía.");
        }

        StringBuilder postfix = new StringBuilder();
        Stack<String> pila = new Stack<>();


        String[] tokens = tokenizar(infix.trim());

        for (String token : tokens) {
            if (token.isEmpty()) continue;

            if (esNumero(token)) {
                if (!postfix.isEmpty() && !postfix.toString().endsWith(" ")) {
                    postfix.append(" ");
                }
                postfix.append(token);

            } else if (token.equals("(")) {
                pila.push(token);

            } else if (token.equals(")")) {
                boolean encontroParentesis = false;
                while (!pila.isEmpty() && !pila.peek().equals("(")) {
                    postfix.append(" ").append(pila.pop());
                }
                if (!pila.isEmpty()) {
                    pila.pop(); // eliminar '('
                    encontroParentesis = true;
                }
                if (!encontroParentesis) {
                    throw new IllegalArgumentException("Paréntesis mal balanceados en la expresión.");
                }

            } else if (esOperador(token)) {
                postfix.append(" ");
                while (!pila.isEmpty() &&
                        !pila.peek().equals("(") &&
                        prioridad(pila.peek()) >= prioridad(token) &&
                        !esAsociativoDerecha(token)) {
                    postfix.append(pila.pop()).append(" ");
                }
                pila.push(token);

            } else {
                throw new IllegalArgumentException("Token inválido: '" + token + "'");
            }
        }

        while (!pila.isEmpty()) {
            String top = pila.pop();
            if (top.equals("(")) {
                throw new IllegalArgumentException("Paréntesis mal balanceados en la expresión.");
            }
            postfix.append(" ").append(top);
        }

        return postfix.toString().trim();
    }


    public static double resolverExpresionPostfix(String postfix) {
        Stack<Double> pila = new Stack<>();
        String[] tokens = postfix.split("\\s+");

        for (String token : tokens) {
            if (token.isEmpty()) continue;

            if (esNumero(token)) {
                pila.push(Double.parseDouble(token));

            } else if (esOperador(token)) {
                if (pila.size() < 2) {
                    throw new IllegalArgumentException("Expresión postfix inválida: operandos insuficientes.");
                }
                double b = pila.pop();
                double a = pila.pop();

                switch (token) {
                    case "+" -> pila.push(a + b);
                    case "-" -> pila.push(a - b);
                    case "*" -> pila.push(a * b);
                    case "/" -> {
                        if (b == 0) throw new ArithmeticException("División por cero no permitida.");
                        pila.push(a / b);
                    }
                    case "^" -> pila.push(Math.pow(a, b));
                    case "%" -> {
                        if (b == 0) throw new ArithmeticException("Módulo por cero no permitido.");
                        pila.push(a % b);
                    }
                }
            }
        }

        if (pila.size() != 1) {
            throw new IllegalArgumentException("Expresión inválida: demasiados operandos.");
        }

        return pila.pop();
    }

    
    private static String[] tokenizar(String infix) {
        // Reemplaza "-" unario al inicio o después de "(" por "0-"
        infix = infix.replaceAll("(?<=[\\(]|^)-\\s*", "0-");
        return infix.split("(?<=\\d|\\))(?=[+\\-*/^%\\)])|(?<=[+\\-*/^%\\(])(?=\\d|\\.)|(?=[\\(\\)])|(?<=[\\(\\)])");
    }

    private static boolean esNumero(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean esOperador(String token) {
        return token.matches("[+\\-*/^%]");
    }

    private static boolean esAsociativoDerecha(String operador) {
        return operador.equals("^");
    }

    
    public static int prioridad(String operador) {
        return switch (operador) {
            case "+", "-" -> 1;
            case "*", "/", "%" -> 2;
            case "^" -> 3;
            default -> 0;
        };
    }
}

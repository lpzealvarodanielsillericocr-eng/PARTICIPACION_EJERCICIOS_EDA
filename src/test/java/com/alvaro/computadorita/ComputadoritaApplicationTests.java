package com.miguel.computadorita;

import com.miguel.computadorita.Tools.Calculadora;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ComputadoritaApplicationTests {

    @Test
    void contextLoads() {}

    @Test
    void testSumaSimple() {
        String postfix = Calculadora.convertirAPostfix("3 + 5");
        double result = Calculadora.resolverExpresionPostfix(postfix);
        assertEquals(8.0, result);
    }

    @Test
    void testPrioridadOperadores() {
        String postfix = Calculadora.convertirAPostfix("2 + 3 * 4");
        double result = Calculadora.resolverExpresionPostfix(postfix);
        assertEquals(14.0, result);
    }

    @Test
    void testParentesis() {
        String postfix = Calculadora.convertirAPostfix("(2 + 3) * 4");
        double result = Calculadora.resolverExpresionPostfix(postfix);
        assertEquals(20.0, result);
    }

    @Test
    void testDivisionDecimal() {
        String postfix = Calculadora.convertirAPostfix("10 / 4");
        double result = Calculadora.resolverExpresionPostfix(postfix);
        assertEquals(2.5, result);
    }

    @Test
    void testPotencia() {
        String postfix = Calculadora.convertirAPostfix("2 ^ 10");
        double result = Calculadora.resolverExpresionPostfix(postfix);
        assertEquals(1024.0, result);
    }

    @Test
    void testDivisionPorCero() {
        String postfix = Calculadora.convertirAPostfix("5 / 0");
        assertThrows(ArithmeticException.class, () -> Calculadora.resolverExpresionPostfix(postfix));
    }

    @Test
    void testExpresionVacia() {
        assertThrows(IllegalArgumentException.class, () -> Calculadora.convertirAPostfix(""));
    }

    @Test
    void testNumeroMultiDigito() {
        String postfix = Calculadora.convertirAPostfix("100 + 200");
        double result = Calculadora.resolverExpresionPostfix(postfix);
        assertEquals(300.0, result);
    }
}

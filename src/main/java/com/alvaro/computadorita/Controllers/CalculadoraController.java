package com.miguel.computadorita.Controllers;

import com.miguel.computadorita.Models.ExpresionRequest;
import com.miguel.computadorita.Models.ExpresionResponse;
import com.miguel.computadorita.Tools.Calculadora;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculadora")
@CrossOrigin(origins = "*")
public class CalculadoraController {

    /**
     * POST /api/calculadora/evaluar
     * Body: { "expresion": "3 + 5 * (2 - 1)" }
     *
     * Convierte infix -> postfix y devuelve el resultado.
     */
    @PostMapping("/evaluar")
    public ResponseEntity<ExpresionResponse> evaluarExpresion(@RequestBody ExpresionRequest request) {

        String infix = request.getExpresion();

        if (infix == null || infix.isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body(new ExpresionResponse(infix, "La expresión no puede estar vacía."));
        }

        try {
            String postfix = Calculadora.convertirAPostfix(infix);
            double resultado = Calculadora.resolverExpresionPostfix(postfix);

            // Si el resultado es entero, mostrarlo sin decimales
            String resultadoStr = (resultado == Math.floor(resultado) && !Double.isInfinite(resultado))
                    ? String.valueOf((long) resultado)
                    : String.valueOf(resultado);

            return ResponseEntity.ok(new ExpresionResponse(infix, postfix, resultadoStr));

        } catch (ArithmeticException e) {
            return ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new ExpresionResponse(infix, "Error aritmético: " + e.getMessage()));

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ExpresionResponse(infix, "Expresión inválida: " + e.getMessage()));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ExpresionResponse(infix, "Error interno: " + e.getMessage()));
        }
    }

    /**
     * GET /api/calculadora/salud
     * Verifica que el servicio esté activo.
     */
    @GetMapping("/salud")
    public ResponseEntity<String> salud() {
        return ResponseEntity.ok("Calculadora en línea ✓");
    }
}

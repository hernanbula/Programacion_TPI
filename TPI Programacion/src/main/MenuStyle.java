package main;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
@author Hernan Cóceres
@author Claudio Rodriguez
@author Hernan E.Bula
@author Gaston Alberto Cejas
 */

    // =========================================
    // STYLE
    // =========================================
    
/**
 * Clase utilitaria para configuraciones de estilo de la consola.
 * Proporciona métodos para asegurar la correcta visualización de caracteres especiales.
 */
public class MenuStyle {
    
    /**
     * Configura la salida estándar para usar codificación UTF-8.
     * Esto asegura que los caracteres especiales del menú se muestren correctamente.
     */
    public static void style(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
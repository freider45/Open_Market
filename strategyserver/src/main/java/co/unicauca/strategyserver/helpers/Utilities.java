package co.unicauca.strategyserver.helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utilidades varias utilizadas por otras clases
 *
 * @author Libardo, Julio
 */
public class Utilities {

    /**
     * Cargar una propiedadd de config.properties
     *
     * @param key llave de la propiedad
     * @return valor de la propiedad
     */
    public static String loadProperty(String key) {
        Properties prop = new Properties();
        InputStream is;

        try {
            is = new FileInputStream("./config.properties");
            prop.load(is);
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo");
        }

        return prop.getProperty(key);
    }
    /**
     * Verifica si un String contiene sólo digitos
     * @param str Cadena a verificvar
     * @return true si contiene sólo digitos, false en caso contrario
     */
    public static boolean isNumeric(String str) {

        boolean resultado;

        try {
            Integer.parseInt(str);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }
}

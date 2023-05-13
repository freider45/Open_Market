package co.unicauca.openmarket.client.infra;

import javax.swing.JOptionPane;

/**
 *
 * @author Libardo Pantoja, Julio A. Hurtado
 */
public class Messages {

    public static void showMessageDialog(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static int showConfirmDialog(String message, String title) {
        return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
     /**
     * Genera un emergente de aventencia
     *
     * @param mns mensaje dentro de la ventana
     * @param title título de la ventana
     */
    public static void warningMessage(String mns, String title) {
        JOptionPane.showMessageDialog(null, mns, title, JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Genera un emergente de error
     *
     * @param mns mensaje dentro de la ventana
     * @param titulo título de la ventana
     */
    public static void errorMessage(String mns, String titulo) {
        JOptionPane.showMessageDialog(null, mns, titulo, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Genera un emergente de exito
     *
     * @param mns mensaje dentro de la ventana
     * @param title título de la ventana
     */
    public static void successMessage(String mns, String title) {
        JOptionPane.showMessageDialog(null, mns, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Genera un emergente de confirmación con los botones Si ó No
     *
     * @param mns mensaje dentro de la ventana
     * @param title título de la ventana
     * @return Si ó No
     */
    public static int confirmMessage(String mns, String title) {
        return JOptionPane.showConfirmDialog(null, mns, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

}

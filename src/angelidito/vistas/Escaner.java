package angelidito.vistas;

import java.io.Closeable;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Clase intermediaria de la clase Scanner. Maneja las excepciones para no
 * lanzar ninguna y guía al usuario a introdocir lo que tiene que introducir.
 *
 * @author <a href="twitter.com/angelidito">Ángel M. D.</a>
 */
public final class Escaner implements Closeable {

    // Boolean indicating if this scanner has been closed
    private static boolean closed;
    private static Scanner escaner;

    static {
        closed = false;
        escaner = new Scanner(System.in);
    }

    /**
     * Abre el escaner si está cerrado.
     */
    public Escaner() {
        if (closed)
            open();
    }

    /**
     * Escanea un número entero positivo por teclado. El método guía al usuario si
     * se empeña en no introducir un número analizable hasta que lo hace.
     *
     * @return El número dentro del rango introducido por el usuario.
     */
    public static int entero() {
        checkNotClosed();

        String textoEscaneado;
        int numeroIntroducido = 0;
        boolean textoEsNumero;

        do {

            textoEscaneado = escaner.nextLine();
            System.out.println();

            try {
                numeroIntroducido = Integer.parseInt(textoEscaneado);
                textoEsNumero = true;

            } catch (NumberFormatException e) {

                System.out.println("Error. Introduzca un número.");
                textoEsNumero = false;

            }

        } while (!textoEsNumero);

        return numeroIntroducido;

    }

    /**
     * Escanea un número entero por teclado. Mayor/menor o igual que 0 según el
     * parámetro. El método guía al usuario si se empeña en no introducir un número
     * analizable hasta que lo hace.
     *
     * @param tipoEntero Tipo de entero que se quiere escanear. POSITIVO, 0 o mayor;
     *                   NEGATIVO, 0 o menor.
     * @return El número dentro del rango introducido por el usuario.
     */
    public static int entero(TipoEntero tipoEntero) {
        checkNotClosed();

        String textoEscaneado;
        int numeroIntroducido = 0;
        boolean textoEsNumeroValido = false;

        do {
            textoEscaneado = escaner.nextLine();
            System.out.println();

            try {
                numeroIntroducido = Integer.parseInt(textoEscaneado);

            } catch (NumberFormatException e) {
                System.out.println("Error. Introduzca un número.");
            }

            switch (tipoEntero) {
                case POSITIVO:
                    if (numeroIntroducido > -1)
                        textoEsNumeroValido = true;
                    break;
                case NEGATIVO:
                    if (numeroIntroducido < 1)
                        textoEsNumeroValido = true;
                    break;
            }

        } while (!textoEsNumeroValido);

        // escaner.close();
        return numeroIntroducido;

    }

    /**
     * Escanea el número entero introducido por teclado. Sólo aceptará un número
     * entero que se encuentre entre los parámetros min y max, ambos inclusive. El
     * método guía al usuario si se empeña en no introducir un número analizable
     * hasta que lo hace. Si min es mayor que max, ambos se fijarán en el mayor de
     * los dos.
     *
     * @param min Valor mínimo der rango
     * @param max Valor máximo der rango
     * @return El número escaneado dentro del rango.
     */
    public static int entero(int min, int max) {
        checkNotClosed();

        if (min > max)
            max = min;


        String textoEscaneado;
        int numeroIntroducido = -999999;
        boolean textoEsNumero = false;

        do {

            textoEscaneado = escaner.nextLine();
            System.out.println();

            try {
                numeroIntroducido = Integer.parseInt(textoEscaneado);
                textoEsNumero = true;

                if (!(min <= numeroIntroducido && numeroIntroducido <= max))
                    System.out.printf("El número debe estar entre %d y %d, ambos inclusive.%n", min, max);

            } catch (NumberFormatException e) {
                System.out.println("Error. Introduzca de nuevo el número.");
            }

        } while (!textoEsNumero || !(min <= numeroIntroducido && numeroIntroducido <= max));

        // escaner.close();
        return numeroIntroducido;

    }

    /**
     * Escanea el dígito introducido por teclado y lo devuelve como un carácter.
     * Sólo aceptará un dígito que se encuentre entre los parámetros min y max,
     * ambos inclusive. El método guía al usuario si se empeña en no intruducir lo
     * que debe hasta que lo hace. Tanto el mínimo como el máximo deben estar entre
     * 0 y 9, ambos inclusive. Si min es mayor que max, ambos se fijaran en el mayor
     * de los dos.
     *
     * @param min Mínimo valor del dígito
     * @param max Máximo valor del dígito
     * @return El carácter correspondiente al número dentro del rango introducido
     * por el usuario.
     */
    public static char caracterNumericoEnRango(int min, int max) {
        checkNotClosed();
        if (min < 0)
            min = 0;
        if (max > 9)
            max = 9;
        if (min > max)
            max = min;

        String textoEscaneado;

        textoEscaneado = escaner.nextLine();
        System.out.println();

        while (textoEscaneado.length() != 1 || !Character.isDigit(textoEscaneado.charAt(0))
                || !(min <= Integer.parseInt(textoEscaneado.substring(0, 1))
                && Integer.parseInt(textoEscaneado.substring(0, 1)) <= max)) {

            if (textoEscaneado.length() != 1) {
                System.out.println("Debe introducir un único número.");
            } else {
                System.out.println("Por favor, escriba un solo número.");
                System.out.printf("Debe de estar entre %d y %d.%n", min, max);
            }

            textoEscaneado = escaner.nextLine();
            System.out.println();

        }

        return textoEscaneado.charAt(0);
    }

    /**
     * Escanea el texto introducido por teclado.
     *
     * @return El texto escaneado.
     */
    public static String texto() {
        checkNotClosed();

        String textoEscaneado = null;
        do {
            try {
                textoEscaneado = escaner.nextLine();
            } catch (NoSuchElementException e) {
                System.err.println("No ha introducido nada.");
                System.out.println("Vuelva a intentarlo.");
            }

        } while (textoEscaneado == null || textoEscaneado.length() < 1);

        // escaner.close();
        return textoEscaneado;
    }

    /**
     * Espera a que el usuario pulse enter.
     */
    public static void Enter() {
        checkNotClosed();
//		do {
        escaner.nextLine();
//		} while (!str.isBlank());
    }

    /**
     * Pide un si o un no y devuelve en consecuencia {@code true} o {@code false}.
     * Escanea el texto introducido por teclado hasta que se introduzca una de las
     * siguiente opciones: s, si, sí, n, no. No atiende a mayúsculas o minúsculas.
     *
     * @return {@code true} o {@code false}, dependiendo si la respusta es si o no.
     */
    public static boolean yesNoQuestion() {
        checkNotClosed();

        String texto;
        boolean textoAdecuado = false;
        boolean yesNo = false;
        do {
            try {
                texto = escaner.nextLine();
            } catch (Exception e) {
                texto = null;
            }

            if (texto != null && (texto.compareToIgnoreCase("s") == 0 || texto.compareToIgnoreCase("sí") == 0
                    || texto.compareToIgnoreCase("si") == 0)) {

                textoAdecuado = true;
                yesNo = true;

            } else if (texto != null && (texto.compareToIgnoreCase("n") == 0 || texto.compareToIgnoreCase("no") == 0)) {
                textoAdecuado = true;
                yesNo = false;
            }

        } while (!textoAdecuado);

        return yesNo;
    }

    /**
     * Pide un si o un no y devuelve en consecuencia {@code true} o {@code false}.
     * Escanea el texto introducido por teclado hasta que se introduzca una de las
     * siguiente opciones: s, si, sí, n, no. No atiende a mayúsculas o minúsculas.
     * METODO CON RECURSIVIDAD. No deberia atascar la máquina si está bien escrito.
     *
     * @return {@code true} o {@code false}, dependiendo si la respusta es si o no.
     */
    public static boolean yesNoQuestionRecursivo() {
        checkNotClosed();

        String texto = "";
        boolean yesNo;

        System.out.println(" [s/n]");
        try {
            texto = escaner.nextLine();
        } catch (Exception e) {

        }
        if (texto.compareToIgnoreCase("s") == 0 || texto.compareToIgnoreCase("sí") == 0
                || texto.compareToIgnoreCase("si") == 0) {

            yesNo = true;

        } else if (texto.compareToIgnoreCase("n") == 0 || texto.compareToIgnoreCase("no") == 0) {

            yesNo = false;

        } else {
            yesNo = yesNoQuestionRecursivo();
        }

        return yesNo;
    }

    /**
     * Imprime por pantalla una pregunta tipo si/no. Además de un salto de linea.
     * Escanea el texto introducido por teclado hasta que se introduzca una de las
     * siguiente opciones: s, si, sí, n, no. No atiende a mayúsculas o minúsculas.
     * METODO CON RECURSIVIDAD. No deberia atascar la máquina si está bien escrito.
     *
     * @param pregunta Pregunta a imprimir por pantalla.
     * @return {@code true} o {@code false}, dependiendo si la respusta es si o no.
     */
    public static boolean yesNoQuestionRecursivo(String pregunta) {
        checkNotClosed();

        String texto = "";
        boolean yesNo;

        System.out.println(pregunta + " [s/n]");
        try {
            texto = escaner.nextLine();
        } catch (Exception e) {

        }
        if (texto.compareToIgnoreCase("s") == 0 || texto.compareToIgnoreCase("sí") == 0
                || texto.compareToIgnoreCase("si") == 0) {

            yesNo = true;

        } else if (texto.compareToIgnoreCase("n") == 0 || texto.compareToIgnoreCase("no") == 0) {

            yesNo = false;

        } else {

            System.out.println("Opción no valida, pruebe de nuevo.");

            yesNo = yesNoQuestionRecursivo();
        }

        // escaner.close();
        return yesNo;
    }

    // METODO CON RECURSIVIDAD

    /**
     * Imprime por pantalla un aviso. Este dice: Asegúrese de que introduce una
     * opción de las disponibles. Ademas, dos saltos de línea.
     */
    public static void avisoOpcionIncorrecta() {
        System.out.println("Asegúrese de que introduce una opción de las disponibles.");
        System.out.println();
    }

    // METODO CON RECURSIVIDAD

    /**
     * Lanza ClosedEscanerRTException si el escaner está cerrado. Si está abierto no
     * hace nada.
     *
     * @throws ClosedEscanerRTException Si el escaner está cerrado.
     */
    private static void checkNotClosed() {
        if (closed)
            throw new ClosedEscanerRTException("El escaner está cerrado.");
    }

    /**
     * Abre de nuevo el escaner. Si y sólo si estába cerrado. Si no, no hace nada.
     */
    public void open() {
        if (closed)
            escaner = new Scanner(System.in);
    }

    /**
     * Si el escaner no está cerrado, lo cierra.
     */
    @Override
    public void close() {
        if (!closed) {
            escaner.close();
            closed = true;
        }

    }

    /**
     * Enumeración empleada en un método.
     *
     * @author <a href="https://twitter.com/angelidito">Ángel M. D.</a>
     */
    public enum TipoEntero {
        POSITIVO, NEGATIVO
    }

    /**
     * Excepción lanzable cuando el escaner está cerrado, pero se quiere usar.
     *
     * @author <a href="https://twitter.com/angelidito">Ángel M. D.</a>
     */
    static class ClosedEscanerRTException extends RuntimeException {

        private static final long serialVersionUID = -431915956388917354L;

        /**
         * @param message Mensaje de la excepción.
         */
        public ClosedEscanerRTException(String message) {
            super(message);
        }
    }
}

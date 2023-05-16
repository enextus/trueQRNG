package org.trueQRNG;

public class LibQRNG {
    // Загрузка библиотеки libQRNG.so
    static {
        System.load("/home/enextus/dev/trueqrng/trueQRNG/lib/libQRNG.so");
    }

    // Объявление нативных функций
    public native int qrng_connect(String username, String password);
    public native int qrng_connect_SSL(String username, String password);
    public native int qrng_get_byte_array(char[] byte_array, int byte_array_size, int[] actual_bytes_rcvd);
    public native int qrng_get_double(double[] value);
    public native int qrng_get_double_array(double[] double_array, int double_array_size, int[] actual_doubles_rcvd);
    public native int qrng_get_int(int[] value);
    public native int qrng_get_int_array(int[] int_array, int int_array_size, int[] actual_ints_rcvd);
    public native int qrng_generate_password(String tobeused_password_chars, char[] generated_password, int password_length);
    public native void qrng_disconnect();

    // Массив возможных параметров
    private static final String[] PARAMETERS = {
            "eduardberlin", "eduardberlin", "eduardberlin",
            "Kc99xl9hOJFY", "Kc99xl9hOJFY", "Kc99xl9hOJFY",
            "to be used password chars",
    };

    // Метод для вызова функций и печати параметров
    public void callFunctions() {
        // Создание экземпляра класса LibQRNG
        LibQRNG qrng = new LibQRNG();

        // Вызов функций
        for (String username : PARAMETERS) {
            for (String password : PARAMETERS) {
                int result = qrng.qrng_connect(username, password);

                // Печать параметров
                System.out.println("Username: " + username);
                System.out.println("Password: " + password);

                // Проверка результата
                if (result == 0) {
                    System.out.println("Connection successful");
                } else {
                    System.out.println("Connection failed");
                }

                System.out.println(); // Пустая строка для отделения вывода
            }
        }

        // Отключение от сервиса
        qrng.qrng_disconnect();
    }

    // Пример использования функции
    public static void main(String[] args) {
        // Создание экземпляра класса LibQRNG
        LibQRNG qrng = new LibQRNG();

        // Вызов функций и печать параметров
        qrng.callFunctions();
    }
}

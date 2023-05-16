package org.trueQRNG;

import java.util.Map;

public class App {
	static {
		try {
			System.load("/home/enextus/dev/trueqrng/trueQRNG/lib/libQRNG.so");
			System.out.println("libQRNG.so was loaded");
		}
		catch (UnsatisfiedLinkError e) {
			System.err.println("Native code library failed to load.\n" + e);
			System.exit(1);
		}
	}

	// Declaration of native functions
	public native int qrng_connect(String username, String password);
	public native int qrng_connect_SSL(String username, String password);
	public native void qrng_disconnect();
	public native void print_qrng_errors();

	public native int qrng_get_int_array(int[] int_array, int int_array_size, int[] actual_ints_rcvd);


	public static final String USERNAME = "eduardberlin";
	public static final String USERPASSWORD = "Kc99xl9hOJFY";

	private static final Map<String, String> CREDENTIALS = Map.of(USERNAME, USERPASSWORD);

	public void callFunctions() {
		try {
			for (Map.Entry<String, String> credential : CREDENTIALS.entrySet()) {



				int result = this.qrng_connect(credential.getKey(), credential.getValue());

				System.out.println("Username: " + credential.getKey());
				System.out.println("Password: " + credential.getValue());

				if (result == 0) {
					System.out.println("Connection successful");
					System.out.println("1000");
				}
				else {
					System.out.println("666");
					throw new RuntimeException("Connection failed with username: " + credential.getKey() + " and password: " + credential.getValue());
				}
				System.out.println();
			}
		}
		finally {
//			this.qrng_disconnect();
		}
	}

	public void getIntArray() {
		// Подключаемся к сервису QRNG
		int connectionResult = this.qrng_connect(USERNAME, USERPASSWORD);
		if (connectionResult != 0) {
			System.out.println("Connection failed with error code: " + connectionResult);
			this.print_qrng_errors();
			return;
		}

		// Создаем массив для получения данных и массив для записи фактического количества полученных чисел
		int arraySize = 100;  // Пример размера массива
		int[] intArray = new int[arraySize];
		int[] actualIntsRcvd = new int[1];

		// Вызываем функцию qrng_get_int_array
		int result = this.qrng_get_int_array(intArray, arraySize, actualIntsRcvd);
		if (result != 0) {
			System.out.println("qrng_get_int_array failed with error code: " + result);
			this.print_qrng_errors();
		} else {
			System.out.println("Received " + actualIntsRcvd[0] + " integers from QRNG service.");
			// Теперь вы можете использовать данные в intArray
			// ...
		}

		// Закрываем соединение
		this.qrng_disconnect();
	}


	public static void main(String[] args) {
		App qrng = new App();

		qrng.callFunctions();
	}
}

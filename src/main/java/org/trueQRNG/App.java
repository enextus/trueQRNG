package org.trueQRNG;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.Map;

public class App {

	static {
		try {
			System.load("/home/enextus/dev/trueqrng/trueQRNG/lib/libQRNG.so");
			System.out.println("libQRNG.so was "
					+ "loaded");
		}
		catch (UnsatisfiedLinkError e) {
			System.err.println("Native code library failed to load.\n" + e);
			System.exit(1);
		}
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

	public static final String USERNAME = "eduardberlin";

	public static final String USERPASSWORD = "Kc99xl9hOJFY";

	private static final Map<String, String> CREDENTIALS = Map.of(USERNAME, USERPASSWORD);

	public void callFunctions() {
		try {
			for (Map.Entry<String, String> credential : CREDENTIALS.entrySet()) {

				System.out.println("credential.getKey(): " + credential.getKey() + " , credential.getValue(): " + credential.getValue());

				int result = this.qrng_connect(credential.getKey(), credential.getValue());

				System.out.println("Username: " + credential.getKey());
				System.out.println("Password: " + credential.getValue());

				if (result == 0) {
					System.out.println("Connection successful");
				}
				else {
					throw new RuntimeException("Connection failed with username: " + credential.getKey() + " and password: " + credential.getValue());
				}

				System.out.println();
			}
		}
		finally {
			this.qrng_disconnect();
		}
	}

	public static void main(String[] args) {

		try {
			String libraryPath = "/home/enextus/dev/trueqrng/trueQRNG/lib/libQRNG.so";

			Process process = Runtime.getRuntime().exec("nm -D " + libraryPath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}

			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}


		App qrng = new App();
		qrng.callFunctions();
	}
}

package org.trueQRNG;

import java.util.*;

import java.io.IOException;
import java.io.InputStream;

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
	public native void qrng_disconnect();
	public native void print_qrng_errors();
	public native int qrng_get_int_array(int[] int_array, int int_array_size, int[] actual_ints_rcvd);
	private static final String CONFIG_FILE = "config.properties";
	private String username;
	private String password;

	public App() {
		loadCredentials();
	}

	private void loadCredentials() {
		try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
			Properties properties = new Properties();
			properties.load(input);

			username = properties.getProperty("username");
			password = properties.getProperty("password");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Map<String, String> getCredentialMap() {
		Map<String, String> credentialMap = new HashMap<>();
		credentialMap.put(username, password);
		return credentialMap;
	}

	public void callFunctions() {
		try {
			for (Map.Entry<String, String> credential : getCredentialMap().entrySet()) {

				List<String> errorMethods = QRNGErrorMethods.getErrorMethods();

				for (String method : errorMethods) {
					System.out.println(method);
				}

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
			this.qrng_disconnect();
		}
	}

	public void getIntArray() {
		// Connect to the QRNG service
		int connectionResult = this.qrng_connect(username, password);
		if (connectionResult != 0) {
			System.out.println("Connection failed with error code: " + connectionResult);
			this.print_qrng_errors();
			return;
		}

		// Create an array to receive data and an array to record the actual number of received numbers
		int arraySize = 100;  // Example array size
		int[] intArray = new int[arraySize];
		int[] actualIntsRcvd = new int[1];

		// Call the qrng_get_int_array function
		int result = this.qrng_get_int_array(intArray, arraySize, actualIntsRcvd);
		if (result != 0) {
			System.out.println("qrng_get_int_array failed with error code: " + result);
			this.print_qrng_errors();
		}
		else {
			System.out.println("Received " + actualIntsRcvd[0] + " integers from QRNG service.");
		}
	}

	public static void main(String[] args) {
		List<String> errorMethods = QRNGErrorMethods.getErrorMethods();

		for (String method : errorMethods) {
			System.out.println(method);
		}

		App qrng = new App();

		qrng.callFunctions();
		qrng.getIntArray();
	}

}

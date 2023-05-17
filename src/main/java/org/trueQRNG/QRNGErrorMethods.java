package org.trueQRNG;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class QRNGErrorMethods {
	public static void main(String[] args) {
		List<String> errorMethods = getErrorMethods();
		for (String method : errorMethods) {
			System.out.println(method);
		}
	}

	static List<String> getErrorMethods() {
		List<String> errorMethods = new ArrayList<>();
		Field[] fields = _qrng_error.class.getFields();

		for (Field field : fields) {
			if (Modifier.isStatic(field.getModifiers()) && field.getType().equals(_qrng_error.class)) {
				String methodName = field.getName();
				String errorMethodName = "qrng_" + methodName.toLowerCase();

				errorMethods.add(errorMethodName);
			}
		}

		return errorMethods;
	}


	private enum _qrng_error {
		QRNG_SUCCESS,
		QRNG_ERR_FAILED_TO_BASE_INIT,
		QRNG_ERR_FAILED_TO_INIT_SOCK,
		QRNG_ERR_FAILED_TO_INIT_SSL,
		QRNG_ERR_FAILED_TO_CONNECT,
		QRNG_ERR_SERVER_FAILED_TO_INIT_SSL,
		QRNG_ERR_FAILED_SSL_HANDSHAKE,
		QRNG_ERR_DURING_USER_AUTH,
		QRNG_ERR_USER_CONNECTION_QUOTA_EXCEEDED,
		QRNG_ERR_BAD_CREDENTIALS,
		QRNG_ERR_NOT_CONNECTED,
		QRNG_ERR_BAD_BYTES_COUNT,
		QRNG_ERR_BAD_BUFFER_ADDRESS,
		QRNG_ERR_PASSWORD_CHARLIST_TOO_LONG,
		QRNG_ERR_READING_RANDOM_DATA_FAILED_ZERO,
		QRNG_ERR_READING_RANDOM_DATA_FAILED_INCOMPLETE,
		QRNG_ERR_READING_RANDOM_DATA_OVERFLOW,
		QRNG_ERR_FAILED_TO_READ_WELCOMEMSG,
		QRNG_ERR_FAILED_TO_READ_AUTH_REPLY,
		QRNG_ERR_FAILED_TO_READ_USER_REPLY,
		QRNG_ERR_FAILED_TO_READ_PASS_REPLY,
		QRNG_ERR_FAILED_TO_SEND_COMMAND
	}
}

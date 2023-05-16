#ifndef LIBQRNG_H
#define LIBQRNG_H

/* error codes */

typedef enum _qrng_error {
	QRNG_SUCCESS = 0,
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

	/* you may obtain between 1 to 2147483647 bytes with one qrng_get_*() call */
	QRNG_ERR_BAD_BYTES_COUNT,
	QRNG_ERR_BAD_BUFFER_ADDRESS,
	QRNG_ERR_PASSWORD_CHARLIST_TOO_LONG,
	QRNG_ERR_READING_RANDOM_DATA_FAILED_ZERO,
	QRNG_ERR_READING_RANDOM_DATA_FAILED_INCOMPLETE,
	/* will be returned if the buffer was filled with more bytes than requested - this should never happen */
	QRNG_ERR_READING_RANDOM_DATA_OVERFLOW,

	QRNG_ERR_FAILED_TO_READ_WELCOMEMSG,
	QRNG_ERR_FAILED_TO_READ_AUTH_REPLY,
	QRNG_ERR_FAILED_TO_READ_USER_REPLY,
	QRNG_ERR_FAILED_TO_READ_PASS_REPLY,
	QRNG_ERR_FAILED_TO_SEND_COMMAND
} qrng_error;

const char *qrng_error_strings[] = {
	"QRNG_SUCCESS",
	"QRNG_ERR_FAILED_TO_BASE_INIT",
	"QRNG_ERR_FAILED_TO_INIT_SOCK",
	"QRNG_ERR_FAILED_TO_INIT_SSL",
	"QRNG_ERR_FAILED_TO_CONNECT",
	"QRNG_ERR_SERVER_FAILED_TO_INIT_SSL",
	"QRNG_ERR_FAILED_SSL_HANDSHAKE",
	"QRNG_ERR_DURING_USER_AUTH",
	"QRNG_ERR_USER_CONNECTION_QUOTA_EXCEEDED",
	"QRNG_ERR_BAD_CREDENTIALS",
	"QRNG_ERR_NOT_CONNECTED",
	"QRNG_ERR_BAD_BYTES_COUNT",
	"QRNG_ERR_BAD_BUFFER_ADDRESS",
	"QRNG_ERR_PASSWORD_CHARLIST_TOO_LONG",
	"QRNG_ERR_READING_RANDOM_DATA_FAILED_ZERO",
	"QRNG_ERR_READING_RANDOM_DATA_FAILED_INCOMPLETE",
	"QRNG_ERR_READING_RANDOM_DATA_OVERFLOW",
	"QRNG_ERR_FAILED_TO_READ_WELCOMEMSG",
	"QRNG_ERR_FAILED_TO_READ_AUTH_REPLY",
	"QRNG_ERR_FAILED_TO_READ_USER_REPLY",
	"QRNG_ERR_FAILED_TO_READ_PASS_REPLY",
	"QRNG_ERR_FAILED_TO_SEND_COMMAND"
};

#ifdef _WIN32
#ifdef DLLEXPORT
#define DLL __declspec(dllexport)
#else
#define DLL __declspec(dllimport)
#endif
#else
#define DLL
#endif

#ifdef __cplusplus
#define EXTERN extern "C"
#else
#define EXTERN extern
#endif

EXTERN DLL const char qrng_libQRNG_version[];

/* All library functions (except disconnect()) return 0 (= QRNG_SUCCESS) if
   the command succeeded, otherwise an error taken from enum _qrng_error.
   Use the qrng_error_strings array to output the error code as a string. */

/* connect to QRNG service first, by default no ssl will be used */
EXTERN DLL int qrng_connect(const char *username, const char *password);
EXTERN DLL int qrng_connect_SSL(const char *username, const char *password);

/* read bytes / double(s) / int(s) (requires an established connection)
   make sure your program allocated the value / array beforehand
   if connected via SSL, the data will be also encrypted */
EXTERN DLL int qrng_get_byte_array(char *byte_array, int byte_array_size, int *actual_bytes_rcvd);
/* returns double value(s) within range [0, 1) */
EXTERN DLL int qrng_get_double(double *value);
EXTERN DLL int qrng_get_double_array(double *double_array, int double_array_size, int *actual_doubles_rcvd);
/* returns integer value(s) */
EXTERN DLL int qrng_get_int(int *value);
EXTERN DLL int qrng_get_int_array(int *int_array, int int_array_size, int *actual_ints_rcvd);
/* this function will return a random string containing the characters a-zA-Z0-9
   of length password_length terminated by a null character */
EXTERN DLL int qrng_generate_password(const char *tobeused_password_chars, char *generated_password, int password_length);

/* here are some handy one-liner functions which automatically a) connect to the QRNG service,
   b) retrieve the requested data and c) disconnect again.
   You can ONLY use them, if you retrieve data only once in a while. Otherwise you'll hit the
   connection quota. Use the qrng_connect() / qrng_get_* / qrng_disconnect() approach then.
   (By default no SSL will be used.) */
EXTERN DLL int qrng_connect_and_get_byte_array(const char *username, const char *password, char *byte_array, int byte_array_size, int *actual_bytes_rcvd);
EXTERN DLL int qrng_connect_and_get_byte_array_SSL(const char *username, const char *password, char *byte_array, int byte_array_size, int *actual_bytes_rcvd);
EXTERN DLL int qrng_connect_and_get_double_array(const char *username, const char *password, double *double_array, int double_array_size, int *actual_doubles_rcvd);
EXTERN DLL int qrng_connect_and_get_double_array_SSL(const char *username, const char *password, double *double_array, int double_array_size, int *actual_doubles_rcvd);
EXTERN DLL int qrng_connect_and_get_int_array(const char *username, const char *password, int *int_array, int int_array_size, int *actual_ints_rcvd);
EXTERN DLL int qrng_connect_and_get_int_array_SSL(const char *username, const char *password, int *int_array, int int_array_size, int *actual_ints_rcvd);

EXTERN DLL int qrng_connect_and_get_double(const char *username, const char *password, double *value);
EXTERN DLL int qrng_connect_and_get_double_SSL(const char *username, const char *password, double *value);
EXTERN DLL int qrng_connect_and_get_int(const char *username, const char *password, int *value);
EXTERN DLL int qrng_connect_and_get_int_SSL(const char *username, const char *password, int *value);

/* disconnect */
EXTERN DLL void qrng_disconnect();

#endif /* LIBQRNG_H */
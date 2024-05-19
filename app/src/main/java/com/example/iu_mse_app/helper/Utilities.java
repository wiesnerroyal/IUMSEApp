package com.example.iu_mse_app.helper;

import android.content.Context;
import android.util.Log;

import com.example.iu_mse_app.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class Utilities {

    public static final String STATUS_OK_JSON_STRING = "{'status':'ok'}'";
    public static final String STATUS_NOK_JSON_STRING = "{'status':'nok'}'";

    // https://stackoverflow.com/questions/58052483/ssl-tls-connection-from-android-to-a-custom-socket-server
    // https://stackoverflow.com/questions/24520833/android-sslsockets-using-self-signed-certificates
    public static SSLContext getSSLContextFromKeyStore(Context context) throws NoSuchAlgorithmException {

        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");

        try {
            KeyStore keyStore = KeyStore.getInstance("BKS");
            InputStream keyInputStream = context.getResources().openRawResource(R.raw.keystore);

            keyStore.load(keyInputStream, context.getString(R.string.keyStorePassword).toCharArray());
            keyInputStream.close();

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    KeyManagerFactory.getDefaultAlgorithm()
            );
            trustManagerFactory.init(keyStore);

            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

            return sslContext;

        } catch (Exception e) {
            Log.e("ERROR", "getSSLContextFromKeyStore: " + e);
        }

        return sslContext;
    }



    // https://stackoverflow.com/questions/309424/how-do-i-read-convert-an-inputstream-into-a-string-in-java
    public static String convertInputStreamToString(InputStream is) throws IOException {

        int bufferSize = 1024;
        char[] buffer = new char[bufferSize];
        StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(is, StandardCharsets.UTF_8);
        for (int numRead; (numRead = in.read(buffer, 0, buffer.length)) > 0; ) {
            out.append(buffer, 0, numRead);
        }
        return out.toString();
    }

    // https://stackoverflow.com/questions/9655181/java-convert-a-byte-array-to-a-hex-string
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

}

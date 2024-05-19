Self-Signed certificate
Info:
https://developer.android.com/privacy-and-security/security-ssl
https://stackoverflow.com/questions/71530525/trust-my-own-self-signed-certificate-in-local-network-using-android-native-andr
https://stackoverflow.com/questions/58052483/ssl-tls-connection-from-android-to-a-custom-socket-server
https://stackoverflow.com/questions/24520833/android-sslsockets-using-self-signed-certificates

1. generate private key and certificate (subjectAltName = IP.1:10.0.2.2)
-----------------------------------------------------------------------
openssl req -newkey rsa:2048 -nodes -x509 -days 36500 -nodes addext "subjectAltName = IP.1:10.0.2.2" -keyout private_key.pem -out certificate.crt

2. generate bks file with portecle
-----------------------------------------------------------------------
java -jar portecle.jar
File -> New Keystore -> BKS
Tools -> Import Trusted Certificate -> (certificate.crt)
File -> Save Keystore -> (enter password)

3. add keystore.bks to raw folder
-----------------------------------------------------------------------
New -> Android Resource Directory-> raw
Copy keystore.bks to raw

4. generate SSLContext from keystore certificate
-----------------------------------------------------------------------
KeyStore keyStore = KeyStore.getInstance("BKS");
InputStream keyInputStream = context.getResources().openRawResource(R.raw.keystore);

keyStore.load(keyInputStream, context.getString(R.string.keyStorePassword).toCharArray());
keyInputStream.close();

TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
trustManagerFactory.init(keyStore);

SSLContext sslContext = SSLContext.getInstance("TLSv1.3");
sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
/**
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS
 * FOR A PARTICULAR PURPOSE. THIS CODE AND INFORMATION ARE NOT SUPPORTED BY XEBIALABS.
 */
package ext.deployit.community.plugin.keystore.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Scanner;
import javax.xml.bind.DatatypeConverter;

class KeyImporter {
  public static void main(String[] args) {

    String deployedName  = args[0];
    String deployedAlias = args[1];
    String deployedCertificate = args[2]; //fileName of DER encoded X.509 chain
    String deployedKey = args[3]; // fileName of DER encoded PKCS8
    String deployedKeyAlgorithm = args[4];
    String deployedKeyPassword = args[5];
    String deployedContainerName = args[6];
    String deployedContainerKeystore = args[7];
    String deployedContainerKeystoreType = args[8];
    String deployedContainerPassphrase = args[9];

    System.out.printf("Install certificate %s to target %s\n", deployedName, deployedContainerName);

    try {
      // Load the keystore
      System.out.printf("Loading keystore %s\n", deployedContainerKeystore);
      KeyStore ks = KeyStore.getInstance(deployedContainerKeystoreType);
      ks.load(new FileInputStream(deployedContainerKeystore), deployedContainerPassphrase.toCharArray());

      // Load certificates (base64 encoded DER/PEM-encoded certificates)
      // http://docs.oracle.com/javase/7/docs/api/java/security/cert/CertificateFactory.html#generateCertificates(java.io.InputStream)
      System.out.println("Loading the certificate(chain)");
      String certificateChainB64 = new Scanner(new File(deployedCertificate)).next();
      ByteArrayInputStream inStream = new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(certificateChainB64));
      CertificateFactory cf = CertificateFactory.getInstance("X.509");
      Certificate[] chain = cf.generateCertificates(inStream).toArray(new java.security.cert.Certificate[0]);
      inStream.close();

      // Save the private key entry (base64 PKCS8 DER encoded)
      System.out.printf("Storing key/certificate %s (alias=%s) in keystore %s\n", deployedName, deployedAlias, deployedContainerKeystore);
      File keyFile = new File(deployedKey);
      String encodedKey = new Scanner(keyFile).next();
      KeyFactory kf = KeyFactory.getInstance(deployedKeyAlgorithm);
      PrivateKey key = kf.generatePrivate(new PKCS8EncodedKeySpec(DatatypeConverter.parseBase64Binary(encodedKey)));
      ks.setEntry(deployedAlias, new KeyStore.PrivateKeyEntry(key, chain), new KeyStore.PasswordProtection(deployedKeyPassword.toCharArray()));
      ks.store(new FileOutputStream(deployedContainerKeystore), deployedContainerPassphrase.toCharArray());

      System.out.printf("Stored key/certificate %s (alias=%s) in keystore %s\n", deployedName, deployedAlias, deployedContainerKeystore);
    } catch (Exception e) {
      System.err.println("Storing key/cerficate failed, cause: " + e.getMessage());
      System.exit(1);
    }
  }
}

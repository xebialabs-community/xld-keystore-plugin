# Keystore plugin #

This document describes the functionality provided by the Keystore plugin.

See the **Deployit Reference Manual** for background information on Deployit and deployment concepts.

# Overview #

The Keystore plugin is a Deployit plugin and allows to package certifcates and keys and deploy them to a keystore.KeyStore.

##Features##
* Install a certificate;
* Modify a certificate / key
* Remove a certificate / key

# Requirements #
* **Deployit requirements**
* **Deployit**: version 3.6+

All certificates/keys are required to be base64 encoded. Suppored formats: certificate X.509 DER, private key PKCS#8, certificate chain PKCS#7

# Usage #

The plugin works with the standard deployment package of DAR format. Please see the [_Packaging Manual_](http://docs.xebialabs.com/releases/4.0/deployit/packagingmanual.html) for more details about the DAR format and the ways to
compose one. 

The following is a sample deployment-manifest.xml file that can be used to install a certificate.
```xml
<?xml version="1.0" encoding="UTF-8"?>
<udm.DeploymentPackage version="1.0" application="CertificateApp">
  <deployables>
    <keystore.TrustedCertificate name="some-ca">
      <alias>some-ca</alias>
      <certificate>MIIF....</certificate>
    </keystore.TrustedCertificate>
    <keystore.TrustedCertificate name="some-ca.replacement">
      <tags />
      <alias>some-ca.replacement</alias>
    </keystore.TrustedCertificate>
    <keystore.PrivateKey name="myprivatekey">
      <alias>myprivatekey</alias>
      <certificate>MII.......</certificate>
      <key>MIIE...</key>
    </keystore.PrivateKey>
  </deployables>
</udm.DeploymentPackage>
```

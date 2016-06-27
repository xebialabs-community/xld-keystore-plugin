<#--

    THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS
    FOR A PARTICULAR PURPOSE. THIS CODE AND INFORMATION ARE NOT SUPPORTED BY XEBIALABS.

-->
echo "Querying whether the key ${deployed.name} is installed on host"
${deployed.container.jvmPath}/bin/keytool -list -keystore ${deployed.container.keystore} -alias ${deployed.alias} -storepass ${deployed.container.passphrase} &> /dev/null
res=$?
if [ $res == 0 ] ; then
	echo "Certificate is already installed"
	exit 1000
fi

echo "Adding key ${deployed.name} to ${deployed.container.keystore} under alias ${deployed.alias}"

echo "Installing ${deployed.name} private key"
mkdir -p ext/deployit/community/plugin/keystore/util
cp KeyImporter.class ext/deployit/community/plugin/keystore/util
${deployed.container.jvmPath}/bin/java ext.deployit.community.plugin.keystore.util.KeyImporter ${deployed.name} ${deployed.alias} certificate-chain.b64 key.b64 ${deployed.keyAlgorithm} ${deployed.keyPassword} ${deployed.container.name} ${deployed.container.keystore} ${deployed.container.keystoreType} ${deployed.container.passphrase}
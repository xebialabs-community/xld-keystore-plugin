<#--

    THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS
    FOR A PARTICULAR PURPOSE. THIS CODE AND INFORMATION ARE NOT SUPPORTED BY XEBIALABS.

-->
<#if deployed.certificate??>
echo "Removing ${deployed.name} under alias ${deployed.alias} from ${deployed.container.keystore}"
${deployed.container.jvmPath}/bin/keytool -delete -keystore ${deployed.container.keystore} -storepass ${deployed.container.passphrase} -alias ${deployed.alias} -noprompt
<#else>
echo "Empty certificate for ${deployed.name}, the keystore will not be modified"
</#if>
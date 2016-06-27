<#--

    THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS
    FOR A PARTICULAR PURPOSE. THIS CODE AND INFORMATION ARE NOT SUPPORTED BY XEBIALABS.

-->
<#if deployed.certificate??>
-----BEGIN CERTIFICATE-----
${statics["com.google.common.base.Joiner"].on("\n").join(
  statics["com.google.common.base.Splitter"].fixedLength(64).split(deployed.certificate))}
-----END CERTIFICATE-----
</#if>
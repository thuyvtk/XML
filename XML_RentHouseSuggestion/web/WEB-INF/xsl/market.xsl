<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : market.xsl
    Created on : March 21, 2020, 8:13 AM
    Author     : ASUS
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:t="thuyvtk.xsd"
                xmlns="thuyvtk.xsd"
                xmlns:xh="http://www.w3.org/1999/xhtml">
    <xsl:output method="xml" indent="yes" omit-xml-declaration="yes"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="t:wiki">
        <xsl:variable name="listDoc" select="document(@link)"/>
        <xsl:variable name="host" select="@link"/>
        
        <xsl:variable name="first" select="$listDoc//a[contains(@title,'Các siêu thị đang hoạt động')]/@href"/>
        
        <xsl:element name="markets">
                
            <xsl:variable name="subString" select="substring-after($first,'Co.opmartamp;')"/>
            <xsl:variable name="subStringBefore" select="concat(concat(substring-before($subString,'amp;'),'&amp;'),substring-after($subString,'amp;'))"/>
            <xsl:variable name="linkEdit" select="concat(concat($host,'?'),$subStringBefore)"/>
            <xsl:variable name="source" select="document(concat($linkEdit,'#C%C3%A1c_si%C3%AAu_th%E1%BB%8B_%C4%91ang_ho%E1%BA%A1t_%C4%91%E1%BB%99ng'))"/>
            <xsl:for-each select="$source//*[string(number(td[position()='1'])) != 'NaN']/td[position()=4]">
                <xsl:element name="market">
                    <xsl:element name="address">
                        <xsl:value-of select="."/>
                    </xsl:element>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>

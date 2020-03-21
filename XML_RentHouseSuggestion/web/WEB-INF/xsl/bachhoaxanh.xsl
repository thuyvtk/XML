<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : bachhoaxanh.xsl
    Created on : March 20, 2020, 7:33 PM
    Author     : ASUS
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:t="thuyvtk.xsd"
                xmlns="thuyvtk.xsd"
                xmlns:xh="http://www.w3.org/1999/xhtml">
    <xsl:output method="xml" indent="yes" omit-xml-declaration="yes"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="t:bachhoaxanh">
        <xsl:variable name="listDoc" select="document(@link)"/>
        <xsl:variable name="host" select="@link"/>
        
        <xsl:variable name="first" select="$listDoc//*[i[@class='bhx-stores']]/a/@href"/>
        <xsl:variable name="second" select="document(concat($host,$first))"/>
        <xsl:variable name="hethongsieuthi" select="concat($host,$first)"/>
        <xsl:variable name ="tpHCM" select="$second//div[@class='listprovince']//a[contains(span,'TP.Hồ Ch')]/@data-url"/>
        <xsl:variable name="tpHCMLink" select="concat(concat($hethongsieuthi,'/'),$tpHCM)"/>
        <xsl:variable name="doc" select="document($tpHCMLink)"/>
        
        <xsl:element name="bachhoaxanh">
        <xsl:for-each select="$doc//div[@class='searchlocal']//option[not(@value='')]">
<!--            <xsl:element name="store">
                <xsl:value-of select="./@data-url"/>
            </xsl:element>-->
            <xsl:call-template name="GetStoreLocationEachDistrict">
                <xsl:with-param name="src" select="document(concat(concat($tpHCMLink,'/'),./@data-url))"/>
            </xsl:call-template>
        </xsl:for-each>
        </xsl:element>
        
    </xsl:template>
    
    <xsl:template name="GetStoreLocationEachDistrict">
        <xsl:param name="src"/>
        
        <xsl:for-each select="$src//ul[@class='listshop']//a[@rel='nofollow']">
            <xsl:element name="store">
                <xsl:value-of select="substring-after(./@href,'query=')"/>
            </xsl:element>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>

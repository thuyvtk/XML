<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : house.xsl
    Created on : December 7, 2019, 4:21 PM
    Author     : ASUS
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" indent="yes" omit-xml-declaration="yes"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="x" page-height="8.5in"
                                       page-width="11in" margin-top="0.5in" margin-bottom="0.5in"
                                       margin-left="1in" >
                    <fo:region-body margin-top="0.5in"/>
                    <fo:region-before extent="1in"/>
                    <fo:region-after extent=".75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="x">
                <fo:static-content flow-name="xsl-region-before">
                    <fo:block font-size="14pt" font-family="Arial"
                              line-height="24pt" background-color = "green"
                              space-after.optimum="15pt" text-align="center"
                              padding-top="3pt">
                        <fo:inline font-family="Arial" color="white">Your House Information</fo:inline>
                    </fo:block>
                </fo:static-content>
                <fo:static-content flow-name="xsl-region-after">
                    <fo:block font-size="18pt" font-family="Arial"
                              line-height="24pt" space-after.optimum="15pt"
                              text-align="center" padding-top="3pt">
                    
                    </fo:block>
                </fo:static-content>
                <fo:flow flow-name="xsl-region-body">
                    <fo:block>
                        <fo:table border-collapse="separate" table-layout="fixed">
                            <fo:table-column column-width="5cm"/>
                            <fo:table-column column-width="5cm"/>
                            <fo:table-column column-width="5cm"/>
                            <fo:table-column column-width="5cm"/>
                            <fo:table-column column-width="5cm"/>
                            <fo:table-column column-width="5cm"/>
                            
                            <fo:table-body>
                                <fo:table-row background-color="blue" color="white">
                                    <fo:table-cell border-color="black"
                                                   border-width="0.5pt"
                                                   border-style="solid">
                                        <fo:block text-align="center"> 
                                            <fo:inline font-family="Arial">Title</fo:inline>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-color="black"
                                                   border-width="0.5pt"
                                                   border-style="solid">
                                        <fo:block text-align="center">
                                            <fo:inline font-family="Arial">Time Posted</fo:inline>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-color="black"
                                                   border-width="0.5pt"
                                                   border-style="solid">
                                        <fo:block text-align="center">
                                            <fo:inline font-family="Arial">Address</fo:inline>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-color="black"
                                                   border-width="0.5pt"
                                                   border-style="solid">
                                        <fo:block text-align="center">
                                            <fo:inline font-family="Arial">Size</fo:inline>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-color="black"
                                                   border-width="0.5pt"
                                                   border-style="solid">
                                        <fo:block text-align="center">
                                            <fo:inline font-family="Arial">Rent Price</fo:inline>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <xsl:for-each select="/*[name()='houses']/*[name()='house']">
                                    <xsl:variable name="count" select="position()" />
                                    <fo:table-row>
                                        <fo:table-cell border-color="black"
                                                       border-width="0.5pt"
                                                       border-style="solid">
                                            <fo:block text-align="center">
                                                <fo:inline font-family="Arial"> 
                                                    <xsl:value-of select="/*[name()='houses']/*[name()='house'][$count]/*[name()='title']/text()"/>
                                                </fo:inline> 
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-color="black"
                                                       border-width="0.5pt"
                                                       border-style="solid">
                                            <fo:block text-align="center">
                                                <fo:inline font-family="Arial">  
                                                    <xsl:value-of select="/*[name()='houses']/*[name()='house'][$count]/*[name()='timePost']/text()"/>
                                                </fo:inline>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-color="black"
                                                       border-width="0.5pt"
                                                       border-style="solid">
                                            <fo:block text-align="center">
                                                <fo:inline font-family="Arial">  
                                                    <xsl:value-of select="/*[name()='houses']/*[name()='house'][$count]/*[name()='rentAddress']/text()"/>
                                                </fo:inline>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-color="black"
                                                       border-width="0.5pt"
                                                       border-style="solid">
                                            <fo:block text-align="center">
                                                <fo:inline font-family="Arial">  
                                                     <xsl:value-of select="/*[name()='houses']/*[name()='house'][$count]/*[name()='size']/text()"/>
                                                </fo:inline>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-color="black"
                                                       border-width="0.5pt"
                                                       border-style="solid">
                                            <fo:block text-align="center">
                                                <fo:inline font-family="Arial">  
                                                    <xsl:value-of select="/*[name()='houses']/*[name()='house'][$count]/*[name()='rentPrice']/text()"/>
                                                </fo:inline>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:for-each>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>

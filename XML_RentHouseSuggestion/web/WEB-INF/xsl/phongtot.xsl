<?xml version="1.0"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:t="thuyvtk.xsd"
                xmlns="thuyvtk.xsd"
                xmlns:xh="http://www.w3.org/1999/xhtml">
    <xsl:output method="xml" indent="yes" omit-xml-declaration="yes"/>

    <xsl:template match="t:house_phongtot">
        <xsl:variable name="listDoc" select="document(@link)"/> 
        <xsl:variable name="host" select="@link"/> 
        <xsl:variable name="room_page_1" select="document($listDoc//ul[@class='nav navbar-nav']//a[ text()='Phòng trọ']/@href)"/>
        <xsl:element name="houses"> 
            <!--<xsl:element name="page">-->
<!--                <xsl:attribute name="page_number">1</xsl:attribute>
                <xsl:attribute name="href">
                    <xsl:value-of select="$listDoc//ul[@class='nav navbar-nav']//a[ text()='Phòng trọ']/@href"/>
                </xsl:attribute>-->
                <!--get list room in page 1-->
                <xsl:for-each select="$room_page_1//div[@class='room-item']//div[@class='block-room-item-title']//a[contains(@href,'http')]"> 
                    <xsl:element name="house"> 
                        <xsl:element name="linkNew">
                            <xsl:value-of select="./@href"/> 
                        </xsl:element>
                        <xsl:call-template name="GetRoomDetail">
                            <xsl:with-param name="srcRoom" select="document(./@href)"/>
                        </xsl:call-template>
                    </xsl:element> 
                </xsl:for-each>
            <!--</xsl:element>-->
            
            <!--get page 2-35-->
            <xsl:for-each select="$room_page_1//ul[@class='pagination']//a[not(@rel='next')]">
                <xsl:if test="substring-after(./@href, '?page=') &lt; 9">
                        <xsl:call-template name="GetLinkPaging">
                            <xsl:with-param name="link" select="./@href"/>
                            <xsl:with-param name="src" select="document(./@href)"/>
                        </xsl:call-template>
                        <xsl:if test="substring-after(./@href, '?page=') = 8">
                            <xsl:call-template name="GetListLinkNextPages">
                                <xsl:with-param name="page" select="substring-after(./@href, '?page=')"/>
                                <xsl:with-param name="linkCurrentPage" select="document(./@href)"/>
                            </xsl:call-template>
                        </xsl:if>
                </xsl:if>
            </xsl:for-each>
        
        </xsl:element>
    </xsl:template> 
    
    <xsl:template name="GetListLinkNextPages">
        <xsl:param name="page" select="1"/>
        <xsl:param name="linkCurrentPage"/>
                <xsl:for-each select="$linkCurrentPage//ul[@class='pagination']//a[not(@rel='next') and not(@rel='prev')]">
                    <xsl:if test="substring-after(./@href, '?page=') &gt; $page">
                        <xsl:call-template name="GetLinkPaging">
                            <xsl:with-param name="link" select="./@href"/>
                            <xsl:with-param name="src" select="document(./@href)"/>
                        </xsl:call-template>
                        <xsl:if test="$page &lt; 36">
                            <xsl:if test="substring-after(./@href, '?page=') = $page+3">
                                <xsl:call-template name="GetListLinkNextPages">
                                    <xsl:with-param name="page" select="substring-after(./@href, '?page=')"/>
                                    <xsl:with-param name="linkCurrentPage" select="document(./@href)"/>
                                </xsl:call-template>
                            </xsl:if>
                        </xsl:if>
                    </xsl:if>
                </xsl:for-each>
    </xsl:template><!--get list urls next pages-->
    
    
    <xsl:template name="GetLinkPaging">
        <xsl:param name="link" select="'No parameter'"/>
        <xsl:param name="src"/>
        <xsl:variable name="pageNumber" select="substring-after($link, '?page=')"/>
        <!--<xsl:if test="$pageNumber &lt; 36">-->
        <xsl:if test="$pageNumber &lt; 9">
            <!--<xsl:element name="page">-->
<!--            <xsl:attribute name="page_number">
                <xsl:value-of select="$pageNumber"/>
            </xsl:attribute>
            <xsl:attribute name="href">
            <xsl:value-of select="$link"/>
            </xsl:attribute>-->
            
            <xsl:for-each select="$src//div[@class='room-item']//div[@class='block-room-item-title']//a[contains(@href,'http')]"> 
                    <xsl:element name="house"> 
                        <xsl:element name="linkNew">
                            <xsl:value-of select="./@href"/> 
                        </xsl:element>
                        
                        <xsl:call-template name="GetRoomDetail">
                            <xsl:with-param name="srcRoom" select="document(./@href)"/>
                        </xsl:call-template>
                        
                    </xsl:element> 
            </xsl:for-each>
            
            <!--</xsl:element>-->
        </xsl:if>
    </xsl:template><!--create attr href-->
    
    <xsl:template name="GetRoomDetail">
        <xsl:param name="srcRoom"/>
        <xsl:element name="title">
            <xsl:value-of select="$srcRoom//div[@class='room-detail']/div[@class='title']//h1/text()"/>
        </xsl:element>
        <xsl:element name="img">
            <xsl:value-of select="$srcRoom//div[@class='room-detail-img']//div[@class='item']/img/@src"/>
        </xsl:element>
        <xsl:element name="rentAddress">
            <xsl:for-each select="$srcRoom//div[@class='main-info']//div[@class='address']/a">
                <xsl:value-of select="concat(.,' ')"/>
            </xsl:for-each>
        </xsl:element>
        <xsl:element name="size">
            <xsl:value-of select="$srcRoom//div[@class='main-info']//div[@class='size']/a[@class='btn info-data']/text()"/>
        </xsl:element>
        <xsl:element name="toilet">
            <xsl:value-of select="$srcRoom//div[@class='main-info']//div[@class='vs']/a[@class='btn info-data']/text()"/>
        </xsl:element>
        <xsl:element name="people">
            <xsl:value-of select="$srcRoom//div[@class='main-info']//div[@class='vs']/a[not(@class='btn info-data')]/text()"/>
        </xsl:element>
        <xsl:element name="electricPrice">
            <xsl:value-of select="$srcRoom//*[span[ @class='btn info-label' and text() = 'Giá nước']]/a[@class='btn info-data']/text()"/>
        </xsl:element>
        <xsl:element name="waterPrice">
            <xsl:value-of select="$srcRoom//*[span[ @class='btn info-label' and text() = 'Giá nước']]/a[not(@class='btn info-data')]/text()"/>
        </xsl:element>
        <xsl:element name="bonus">
            <xsl:value-of select="$srcRoom//*[span[ @class='btn info-label' and text() = 'Tiện ích']]/a"/>
        </xsl:element>
        <xsl:element name="rentPrice">
            <xsl:value-of select="$srcRoom//div[@class='info-price']/a/text()"/>
        </xsl:element>
        <xsl:element name="detail">
            <xsl:value-of select="$srcRoom//div[@class='dis-content']"/>
        </xsl:element>
    </xsl:template><!--get detail rent house new-->

</xsl:stylesheet>

<?xml version="1.0"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:t="thuyvtk.xsd"
                xmlns="thuyvtk.xsd"
                xmlns:xh="http://www.w3.org/1999/xhtml">
    <xsl:output method="xml" indent="yes" omit-xml-declaration="yes"/>

    <!--main template, apply root-->
    <xsl:template match="domain">
        <xsl:element name="houses">
            <xsl:apply-templates/>
        </xsl:element>
    </xsl:template>
    
    
    <!--    template crawl thuenhatro360-->    
    <xsl:template match="t:house_thuenhatro360">
        <xsl:variable name="home" select="document(@link)"/> 
        <xsl:variable name="homeDomain" select="@link"/>
        <xsl:for-each select="$home//div[@class='header-left']//li//a[text()='Hồ Chí Minh' or text()='Hà Nội']">
            
            <xsl:call-template name="CrawlElementThueNhaTro360">
                <xsl:with-param name="firstPage" select="document(./@href)"/>
                <xsl:with-param name="host" select="$homeDomain"/>
            </xsl:call-template>

        </xsl:for-each>
                
    </xsl:template>
    
    <!--crawl thuenhatro360, ul HCM & ul HN-->
    <xsl:template name="CrawlElementThueNhaTro360">
        <xsl:param name="firstPage" select="'No param'"/>
        <xsl:param name="host" select="'No param'"/>
        
        <xsl:call-template name="CrawlHouseThueNhaTro360">
            <xsl:with-param name="page" select="$firstPage"/>
            <xsl:with-param name="hostDomain" select="$host"/>
        </xsl:call-template>
        
        <xsl:for-each select="$firstPage//div[@class='paginate-items']//li//a[not(@rel='next') and not(rel='prev') and text()&lt;1]">
            <xsl:call-template name="CrawlHouseThueNhaTro360">
                <xsl:with-param name="page" select="document(./@href)"/>
                <xsl:with-param name="hostDomain" select="$host"/>
            </xsl:call-template>
            <xsl:if test="substring-after(./@href, '?page=') = 8">
                <xsl:call-template name="CrawlNextPageThueNhaTro360">
                    <xsl:with-param name="currentPage" select="document(./@href)"/>
                    <xsl:with-param name="pageNumber" select="substring-after(./@href, '?page=')"/>
                    <xsl:with-param name="hostDomain" select="$host"/>
                </xsl:call-template>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>
    <!--crawl nextpage-->
    <xsl:template name="CrawlNextPageThueNhaTro360">
        <xsl:param name="currentPage" select="'No param'"/>
        <xsl:param name="pageNumber" select="'8'"/>
        <xsl:param name="hostDomain" select="'no param'"/>
        
        <xsl:for-each select="$currentPage//div[@class='paginate-items']//li//a[not(@rel='next') and not(rel='prev') and text()&gt;$pageNumber and text()&lt;$pageNumber+4]">
            <xsl:call-template name="CrawlHouseThueNhaTro360">
                <xsl:with-param name="page" select="$currentPage"/>
                <xsl:with-param name="hostDomain" select="$hostDomain"/>
            </xsl:call-template>
        </xsl:for-each>
    </xsl:template>
    <!--crawl new thuenhatro360-->
    <xsl:template name="CrawlHouseThueNhaTro360">
        <xsl:param name="page" select="'no param'"/>
        <xsl:param name="hostDomain" select="'no param'"/>
        <xsl:for-each select="$page//article//a[@class='advert_hoz_item_img']">
            <xsl:element name="house">
                <xsl:element name="linkNew">
                    <xsl:value-of select="concat($hostDomain,./@href)"/> 
                </xsl:element>
                
                <xsl:call-template name="GetDetailThueNhaTro360">
                    <xsl:with-param name="srcNew" select="document(concat($hostDomain,./@href))"/>
                </xsl:call-template>
                
            </xsl:element>
        </xsl:for-each>
    </xsl:template>
    <!--crawl detail new-->
    <xsl:template name="GetDetailThueNhaTro360">
        <xsl:param name="srcNew"/>
        <xsl:element name="title">
            <xsl:value-of select="$srcNew//div[@class='advert_detail']//h1/text()"/>
        </xsl:element>
        <xsl:element name="timePost">
            <xsl:call-template name="RemoveSpace">
                <xsl:with-param name="string" select="$srcNew//p[i[@class='material-icons' and text()='access_time']]/text()"/>
            </xsl:call-template>
        </xsl:element>
        <xsl:element name="img">
            <xsl:value-of select="$srcNew//div[@class='advert_detail']/amp-carousel/@src"/>
        </xsl:element>
        <xsl:element name="rentAddress">
            <xsl:value-of select="$srcNew//p[i[@class='material-icons' and text()='location_on']]/text()"/>
        </xsl:element>
        <xsl:element name="size">
            <xsl:value-of select="$srcNew//*[i[@class='material-icons' and text()='photo_size_select_small']]/span/text()"/>
        </xsl:element>
        <xsl:element name="electricPrice">
            <xsl:value-of select="$srcNew//*[span[ @class='btn info-label' and text() = 'Giá nước']]/a[@class='btn info-data']/text()"/>
        </xsl:element>
        <xsl:element name="waterPrice">
            <xsl:value-of select="$srcNew//*[span[ @class='btn info-label' and text() = 'Giá nước']]/a[not(@class='btn info-data')]/text()"/>
        </xsl:element>
        <xsl:element name="bonus">
            <xsl:for-each select="$srcNew//div[@class='utilities']//div[@class='utility ']//p//text()">
                <xsl:value-of select="concat(.,',')"/>
            </xsl:for-each>
        </xsl:element>
        <xsl:element name="rentPrice">
            <xsl:call-template name="RemoveSpace">
                <xsl:with-param name="string" select="$srcNew//h2[amp-img[@src='https://thuenhatro360.com/images/money-bag.svg']]"/>
            </xsl:call-template>
        </xsl:element>
        <xsl:element name="detail">
            <xsl:for-each select="$srcNew//div[@class='advert_detail']/div[br]/text()">
                <xsl:if test="contains(.,'điện') or contains(.,'nước')">
                    <xsl:value-of select="concat(.,' ')"/>
                </xsl:if>
            </xsl:for-each>
        </xsl:element>
        <xsl:element name="latitude">
            <xsl:call-template name="GetLatituteLongtitute">
                <xsl:with-param name="string" select="$srcNew//amp-iframe[contains(@src,'https://www.google.com/maps/embed')]/@src"/>
                <xsl:with-param name="return" select="'latitude'"/>
            </xsl:call-template>
        </xsl:element>
        <xsl:element name="longitude">
            <xsl:call-template name="GetLatituteLongtitute">
                <xsl:with-param name="string" select="$srcNew//amp-iframe[contains(@src,'https://www.google.com/maps/embed')]/@src"/>
                <xsl:with-param name="return" select="'longitude'"/>
            </xsl:call-template>
        </xsl:element>
    </xsl:template><!--get detail rent house new-->
    
    <xsl:template name="GetLatituteLongtitute">
        <xsl:param name="string"/>
        <xsl:param name="return"/>
        
        <xsl:if test="$return='latitude'">
            <xsl:value-of select="substring-before(substring-before(substring-after($string,'AIzaSyAV5VZKtqt2jNk1ZBBOL9bFDGC1XWAmFroq='),'zoom=15'),',')"/>
        </xsl:if>
        <xsl:if test="$return='longitude'">
            <xsl:value-of select="substring-after(substring-before(substring-after($string,'AIzaSyAV5VZKtqt2jNk1ZBBOL9bFDGC1XWAmFroq='),'zoom=15'),',')"/>
        </xsl:if>
    </xsl:template>
    
    <xsl:template name="RemoveSpace">
        <xsl:param name="string"/>
        <xsl:value-of select="normalize-space($string)"/>
    </xsl:template>
    <!--Crawl phongtot-->
    <xsl:template match="t:house_phongtot">
        <xsl:variable name="listDoc" select="document(@link)"/> 
        <xsl:variable name="host" select="@link"/> 
        <xsl:variable name="room_page_1" select="document($listDoc//ul[@class='nav navbar-nav']//a[ text()='Phòng trọ']/@href)"/>
         
        <xsl:element name="page">
            <xsl:attribute name="page_number">1</xsl:attribute>
            <xsl:attribute name="href">
                <xsl:value-of select="$listDoc//ul[@class='nav navbar-nav']//a[ text()='Phòng trọ']/@href"/>
            </xsl:attribute>
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
        </xsl:element>
            
        <!--get page 2-35-->
        <xsl:for-each select="$room_page_1//ul[@class='pagination']//a[not(@rel='next')]">
            <xsl:if test="substring-after(./@href, '?page=') &lt; 3">
                <xsl:call-template name="GetLinkPaging">
                    <xsl:with-param name="link" select="./@href"/>
                    <xsl:with-param name="src" select="document(./@href)"/>
                </xsl:call-template>
                <!--                <xsl:if test="substring-after(./@href, '?page=') = 8">
                    <xsl:call-template name="GetListLinkNextPages">
                        <xsl:with-param name="page" select="substring-after(./@href, '?page=')"/>
                        <xsl:with-param name="linkCurrentPage" select="document(./@href)"/>
                    </xsl:call-template>
                </xsl:if>-->
            </xsl:if>
        </xsl:for-each>
        
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
            <xsl:if test="contains($srcRoom//div[@class='dis-content'],'điện') or contains($srcRoom//div[@class='dis-content'],'nước')">
                <xsl:value-of select="$srcRoom//div[@class='dis-content']"/>
            </xsl:if>
        </xsl:element>
    </xsl:template><!--get detail rent house new-->
    
    

</xsl:stylesheet>

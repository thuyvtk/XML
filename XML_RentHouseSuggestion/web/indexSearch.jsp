<%-- 
    Document   : indexSearch
    Created on : Feb 28, 2020, 10:12:16 AM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home suggestion</title>
        <link rel="stylesheet" href="css/default.css"/>
        <link href="https://fonts.googleapis.com/css?family=Titillium+Web&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Fjalla+One&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro&display=swap" rel="stylesheet">
        <script type="text/javascript" src="js/default.js"></script>
    </head>
    <body onload="return displaySearchOptionSpace()">
        <!--        header-->
        <div class="header_container">
            <div class="logo" onclick="location.href = ''"></div>
            <div class="line">
            </div>
            <div class="love_list"></div>
            <div class="login" onclick="location.href = 'loginPage.html'"></div>
        </div>

        <!--search space-->
        <div class="search_container">
            <div id="search_space">
                <input id="search_bar" class="controls" type="text" name="txtSearchValue" placeholder="Tìm theo khu vực, địa điểm..." />
                <form action="ProcessServlet" method="POST">

                    <input type="submit" value="Search" name="action" id="btn_Search"/>
                    <input type="hidden" name="latitude" value="" id="latitude_param" />
                    <input type="hidden" name="longitude" value="" id="longitude_param" />
                </form>

                <br/>
                <input id="btn_MoreOption" type="submit" value="Tùy chọn nâng cao" name="btnMoreOption" onclick="return displaySearchOptionSpace()"/>

            </div>
        </div>

        <div class="result-item">
            <div class="top-houses-lable">
                <h1 class="lable">Best Suggestion</h1>
            </div>
            <div class="top-4item">
                <%--<c:set var='top4' value="${requestScope.TOP4}"/>--%>
                <%--<c:if test="${not empty top4}">--%>
                <%--<c:forEach var="top" items="${top4}">--%>
                <div class="item">
                    <div class="image-box">
                    <img class="item-img" src="https://thuenhatro360.com/u/i/t6gqhKxw.jpg"/> 
                    </div>
                    <div class="item-price">$ 2 triệu 700 ngàn/ Tháng</div>
                    
                    <div class="item-bonus">
                        <div class="bonus">
                            <img src="css/icons_washing_machine.png"/><br/>
                            Máy giặt
                        </div>
                        <div class="bonus">
                            <img src="css/icons_fridge.png"/><br/>
                            Tủ lạnh
                        </div>
                        <div class="bonus">
                            <img src="css/icons_air_conditioner.png"/><br/>
                            Điều hòa
                        </div>
                        <div class="bonus">
                            <img src="css/icons_scooter.png"/><br/>
                            Đậu xe
                        </div>
                    </div>
                    
                    <div class="action">
                        <input type="submit" value="Browse" name="action" class="btnBrowse"/>
                        <input type="submit" value="love" name="action" class="love"/>
                    </div>
                </div>
                
                
                <div class="item">
                    <div class="image-box">
                    <img class="item-img" src="https://thuenhatro360.com/u/i/t6gqhKxw.jpg" 
                         alt="GeeksforGeeks logo"> 
                    </div>
                </div>
                <div class="item">
                    <div class="image-box">
                    <img class="item-img" src="https://thuenhatro360.com/u/i/t6gqhKxw.jpg" 
                         alt="GeeksforGeeks logo"> 
                    </div>
                </div>
                <%--</c:forEach>--%>
                <%--</c:if>--%>
            </div>
        </div>

        <div id="map"></div>
        <!--API key google map-->
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDcw-KnCAPzni3QYvgk3RDoTZL65xh8a4o&libraries=places&language=vi&region=VI&callback=initAutocomplete"
        async defer></script>
    </body>
</html>

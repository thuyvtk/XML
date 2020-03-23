<%-- 
    Document   : indexSearch
    Created on : Feb 28, 2020, 10:12:16 AM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home suggestion</title>
        <link rel="stylesheet" href="css/default.css"/>
        <link href="https://fonts.googleapis.com/css?family=IBM+Plex+Serif&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Fjalla+One&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro&display=swap" rel="stylesheet">
        <script type="text/javascript" src="js/default.js"></script>
    </head>
    <body onload="return displaySearchOptionSpace()">
        <script>
            regObj = '${requestScope.INFO}';
        </script>
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

                <form action="ProcessServlet" method="POST" name="myForm" >
                    <input id="search_bar" class="controls" type="text" name="txtSearchValue" placeholder="Tìm theo khu vực, địa điểm..." />
                    <input type="submit" value="Search" name="action" id="btn_Search"/>
                    <input type="hidden" name="latitude" value="" id="latitude_param" />
                    <input type="hidden" name="longitude" value="" id="longitude_param" />
                </form>

                <form action="ProcessServlet" method="POST" id="searchServerForm" >
                    <input type="hidden" name="searchValue" value="" id="searchValue" />
                    <input style="width: 50%; height: 30px;background-color: lightpink;color: white;margin-left: 150px;margin-top: 20px;border-radius: 2px" type="button" name="btSearch" value="Search" class="btn_insert"
                           onclick="return searchProcess();"/>
                </form>
                <br/>
                <input id="btn_MoreOption" type="submit" value="Tùy chọn nâng cao" name="btnMoreOption" onclick="return displaySearchOptionSpace()"/>

            </div>
        </div>

        <div class="result-item">
            <div class="top-houses-lable">
                <h1 class="lable">3 Đề Xuất Tốt Nhất</h1>
            </div>
            <div class="top-4item" id="listHouse">
                <c:set var='top4' value="${requestScope.TOP4}"/>
                <c:if test="${not empty top4}">
                    <c:forEach var="top" items="${top4}">
                        <div class="top-item">
                            <div class="item">
                                <div class="image-box">
                                    <img class="item-img" src=${top.img}/> 
                                </div>
                                <div class="item-price">${top.rentPrice}<br/>
                                    <div class="item-location">
                                        <img src="css/icon_previous_location.png"/>
                                        <span>${top.distance} km</span>
                                    </div>
                                    <div class="item-market">
                                        Coop-mart: <span>5km</span>
                                    </div>
                                </div>



                                <div class="item-bonus">
                                    <c:if test="${top.bonusDTO.washing}">
                                        <div class="bonus">
                                            <img src="css/icons_washing_machine.png"/><br/>
                                            Máy giặt
                                        </div>
                                    </c:if>
                                    <c:if test="${top.bonusDTO.fridge}">
                                        <div class="bonus">
                                            <img src="css/icons_fridge.png"/><br/>
                                            Tủ lạnh
                                        </div>
                                    </c:if>
                                    <c:if test="${top.bonusDTO.air_conditioner}">
                                        <div class="bonus">
                                            <img src="css/icons_air_conditioner.png"/><br/>
                                            Điều hòa
                                        </div>
                                    </c:if>
                                    <c:if test="${top.bonusDTO.parking}">
                                        <div class="bonus">
                                            <img src="css/icons_scooter.png"/><br/>
                                            Đậu xe
                                        </div>
                                    </c:if>
                                    <c:if test="${top.bonusDTO.heater}">
                                        <div class="bonus">
                                            <img src="css/icons_shower.png"/><br/>
                                            Bình nóng lạnh
                                        </div>
                                    </c:if>
                                </div>

                                <div class="action">
                                    <a href=${top.linkNew} target="_blank"><input type="submit" value="Website" name="action" class="btnBrowse"/></a>
                                    <input type="submit" value="love" name="action" class="love"/>
                                </div>
                            </div>
                        </div>

                    </c:forEach>
                </c:if>
                <c:set var='listHouse' value="${requestScope.LISTHOUSE}"/>
                 <c:if test="${not empty listHouse}">
                    <c:forEach var="top" items="${listHouse}">
                        <div class="top-item">
                            <div class="item">
                                <div class="image-box">
                                    <img class="item-img" src=${top.img}/> 
                                </div>
                                <div class="item-price">${top.rentPrice}<br/>
                                  
                                </div>
                                <div class="action">
                                    <a href=${top.linkNew} target="_blank"><input type="submit" value="Website" name="action" class="btnBrowse"/></a>
                                    <input type="submit" value="love" name="action" class="love"/>
                                </div>
                            </div>
                        </div>

                    </c:forEach>
                </c:if>
            </div>

            <div class="other-items">
                <div class="top-houses-lable">
                    <h1 class="lable">Các Đề Xuất Khác</h1>
                    <h5>Các đề xuất này đã được sắp xếp theo đánh giá qua các đặc điểm</h5>
                </div>

                <div class="row-1">
                    <div class="row-item">
                        <div class="item">
                            <div class="image-box">
                                <img class="item-img" src="https://thuenhatro360.com/u/i/t6gqhKxw.jpg"/> 
                            </div>
                            <div class="item-price">$ 2 triệu 700 ngàn/ Tháng<br/>
                                <div class="item-location">
                                    <img src="css/icon_previous_location.png"/>
                                    <span>8.5km</span>
                                </div>
                                <div class="item-market">
                                    Coop-mart: <span>5km</span>
                                </div>
                            </div>



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
                    </div>

                    <div class="row-item">
                        <div class="item">
                            <div class="image-box">
                                <img class="item-img" src="https://thuenhatro360.com/u/i/t6gqhKxw.jpg"/> 
                            </div>
                            <div class="item-price">$ 2 triệu 700 ngàn/ Tháng<br/>
                                <div class="item-location">
                                    <img src="css/icon_previous_location.png"/>
                                    <span>8.5km</span>
                                </div>
                                <div class="item-market">
                                    Coop-mart: <span>5km</span>
                                </div>
                            </div>



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
                    </div>

                </div>
            </div>
        </div>

        <div id="map"></div>
        <!--API key google map-->
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDcw-KnCAPzni3QYvgk3RDoTZL65xh8a4o&libraries=places&language=vi&region=VI&callback=initAutocomplete"
        async defer></script>
    </body>
</html>

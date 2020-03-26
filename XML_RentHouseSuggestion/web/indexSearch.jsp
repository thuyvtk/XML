<%-- 
    Document   : indexSearch
    Created on : Feb 28, 2020, 10:12:16 AM
    Author     : ASUS
--%>

<%@page import="thuyvtk.dto.HouseDTO"%>
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
        <link href="https://fonts.googleapis.com/css?family=Alegreya&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Lora&display=swap" rel="stylesheet">
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
            <form action="ProcessServlet" method="POST">
                <input type="submit" value="View love list" name="action" class="love_list"/>
            </form>

            <div class="login" onclick="location.href = 'loginPage.html'"></div>
        </div>

        <!--search space-->
        <div class="search_container" id="search_container">
            <div id="search_space">
                <form action="ProcessServlet" id="searchServerForm">
                    <input id="search_like" class="controls" type="text" name="txtSearchValue" placeholder="Tìm theo khu vực, địa điểm..." />
                    <input type="button" value="Search " name="action" id="btn_SearchLike"  onclick="return searchProcess();"/>
                </form>
                <form action="ProcessServlet">
                    <input type="submit" value="Search" name="action" id="btn_Search"  onclick="checkNull()"/>
                    <input type="hidden" name="latitude" value="" id="latitude_param" />
                    <input type="hidden" name="longitude" value="" id="longitude_param" />
                    <input type="hidden" name="searchWay" value="" id="search-way" />


                    <br/>
                    <div class="moreOptional" id="div-optional">
                        <input id="search_bar" class="controls" type="text" name="txtSearchValue" placeholder="Bạn muốn thuê gần..."/>
                        <div class="cb-distance">
                            <span>Khoảng cách tối đa:</span>
                            <select name="dropdownDistance">
                                <option value="5">5 KM</option>
                                <option value="3">3 KM</option>
                                <option value="8">8 KM</option>
                                <option value="10">10 KM</option>
                                <option value="15">15 KM</option>
                            </select>
                        </div>
                        <div class="prioritize">
                            <span> Ưu tiên diện tích:</span>
                            <input type="checkbox" name="cbSize" value="true" checked="checked"/>
                        </div>
                        <div class="prioritize-bonus">
                            <span> Tiện ích:</span>
                            <div class="search-bonus">
                                <div class="bonus-search">
                                    <input type="checkbox" name="cbFridge" value="ON" checked="checked" />
                                    <img src="css/icons8_fridge_48.png"/><br/>
                                    Tủ lạnh<br/>
                                </div>
                                <div class="bonus-search">
                                    <input type="checkbox" name="cbAir" value="ON" checked="checked" />
                                    <img src="css/icons8_air_conditioner.png"/><br/>
                                    Điều hòa<br/>
                                </div>
                                <div class="bonus-search">
                                    <input type="checkbox" name="cbWashing" value="ON" checked="checked" />
                                    <img src="css/icons_washing_machine_1.png"/><br/>
                                    Máy giặt<br/>
                                </div>
                            </div>
                            <div class="search-bonus">
                                <div class="bonus-search">
                                    <input type="checkbox" name="cbParking" value="ON" checked="checked" />
                                    <img src="css/icons_scooter_50.png"/><br/>
                                    Đậu xe<br/>
                                </div>
                                <div class="bonus-search">
                                    <input type="checkbox" name="cbShower" value="ON" checked="checked" />
                                    <img src="css/icons8_safety_shower.png"/><br/>
                                    Bình nóng lạnh<br/>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
                <span id="label-optional">Tất cả các tiêu chí vừa chọn sẽ được đánh giá cùng với giá thuê để đưa ra kết quả tốt nhất!</span>
                <input id="btn_MoreOption" type="submit" value="Tùy chọn nâng cao" name="btnMoreOption" onclick="return displaySearchOptionSpace()"/>

            </div>
        </div>
        <div  class="result-item">
            <div class="top-4item" id="listHouse">
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
        </div>               

        <c:set var='result' value="${requestScope.LIST_HOUSE}"/>
        <c:if test="${not empty result}">
            <div class="result-item">
                <div class="top-houses-lable">
                    <h1 class="lable">3 Đề Xuất Tốt Nhất</h1>
                </div>
                <div class="top-4item">
                    <c:set var='top4' value="${requestScope.TOP4}"/>
                    <c:if test="${not empty top4}">
                        <c:forEach var="top" items="${top4}" varStatus="counter">
                            <div class="top-item">
                                <div class="item">
                                    <div class="image-box">
                                        <img class="item-img" src=${top.img}/> 
                                    </div>
                                    <div class="item-price">$ ${top.rentPrice}<br/>
                                        <div class="item-location">
                                            <img src="css/icon_previous_location.png"/>
                                            <span>${top.distance} km</span>
                                        </div>
                                        <div class="item-market">
                                            Coop-mart: <span>${top.marketDistance} km</span>
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
                                        <form action="ProcessServlet" method="POST">
                                            <input type="submit" value="love" name="action" class="love"/>
                                            <input type="hidden" name="index" value="${counter.count-1}" />
                                        </form>
                                    </div>
                                </div>
                            </div>

                        </c:forEach>
                    </c:if>
                </div>
                <%--<c:set var="item4" value="${result.}"--%>
                <c:if test="${not empty result[3]}">


                    <div class="other-items">
                        <div class="top-houses-lable">
                            <h1 class="lable">Các Đề Xuất Khác</h1>
                            <h5>Các đề xuất này đã được sắp xếp theo đánh giá qua các đặc điểm</h5>
                        </div>
                        <c:forEach var="other" items="${result}" varStatus="counter">
                            <c:if test="${counter.count%4 == 0}">
                                <div class="row-1">
                                    <div class="row-item">
                                        <div class="item">
                                            <div class="image-box">
                                                <img class="item-img" src=${other.img}/> 
                                            </div>
                                            <div class="item-price">$ ${other.rentPrice}<br/>
                                                <div class="item-location">
                                                    <img src="css/icon_previous_location.png"/>
                                                    <span>${other.distance} km</span>
                                                </div>
                                                <div class="item-market">
                                                    Coop-mart: <span>${other.marketDistance} km</span>
                                                </div>
                                            </div>

                                            <div class="item-bonus">
                                                <c:if test="${other.bonusDTO.washing}">
                                                    <div class="bonus">
                                                        <img src="css/icons_washing_machine.png"/><br/>
                                                        Máy giặt
                                                    </div>
                                                </c:if>
                                                <c:if test="${other.bonusDTO.fridge}">
                                                    <div class="bonus">
                                                        <img src="css/icons_fridge.png"/><br/>
                                                        Tủ lạnh
                                                    </div>
                                                </c:if>
                                                <c:if test="${other.bonusDTO.air_conditioner}">
                                                    <div class="bonus">
                                                        <img src="css/icons_air_conditioner.png"/><br/>
                                                        Điều hòa
                                                    </div>
                                                </c:if>
                                                <c:if test="${other.bonusDTO.parking}">
                                                    <div class="bonus">
                                                        <img src="css/icons_scooter.png"/><br/>
                                                        Đậu xe
                                                    </div>
                                                </c:if>
                                                <c:if test="${other.bonusDTO.heater}">
                                                    <div class="bonus">
                                                        <img src="css/icons_shower.png"/><br/>
                                                        Bình nóng lạnh
                                                    </div>
                                                </c:if>
                                            </div>

                                            <div class="action">
                                                <a href=${other.linkNew} target="_blank"><input type="submit" value="Website" name="action" class="btnBrowse"/></a>
                                                <form action="ProcessServlet" method="POST">
                                                    <input type="submit" value="love" name="action" class="love"/>
                                                    <input type="hidden" name="index" value="${counter.count-1}" />
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                    <c:if test="${not empty result[counter.count]}">
                                        <div class="row-item">
                                            <div class="item">
                                                <div class="image-box">
                                                    <img class="item-img" src=${result[counter.count].img}/> 
                                                </div>
                                                <div class="item-price">$ ${result[counter.count].rentPrice}<br/>
                                                    <div class="item-location">
                                                        <img src="css/icon_previous_location.png"/>
                                                        <span>${result[counter.count].distance} km</span>
                                                    </div>
                                                    <div class="item-market">
                                                        Coop-mart: <span>${result[counter.count].marketDistance} km</span>
                                                    </div>
                                                </div>

                                                <div class="item-bonus">
                                                    <c:if test="${result[counter.count].bonusDTO.washing}">
                                                        <div class="bonus">
                                                            <img src="css/icons_washing_machine.png"/><br/>
                                                            Máy giặt
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${result[counter.count].bonusDTO.fridge}">
                                                        <div class="bonus">
                                                            <img src="css/icons_fridge.png"/><br/>
                                                            Tủ lạnh
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${result[counter.count].bonusDTO.air_conditioner}">
                                                        <div class="bonus">
                                                            <img src="css/icons_air_conditioner.png"/><br/>
                                                            Điều hòa
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${result[counter.count].bonusDTO.parking}">
                                                        <div class="bonus">
                                                            <img src="css/icons_scooter.png"/><br/>
                                                            Đậu xe
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${result[counter.count].bonusDTO.heater}">
                                                        <div class="bonus">
                                                            <img src="css/icons_shower.png"/><br/>
                                                            Bình nóng lạnh
                                                        </div>
                                                    </c:if>
                                                </div>

                                                <div class="action">
                                                    <a href=${result[counter.count].linkNew} target="_blank"><input type="submit" value="Website" name="action" class="btnBrowse"/></a>
                                                    <form action="ProcessServlet" method="POST">
                                                        <input type="submit" value="love" name="action" class="love"/>
                                                        <input type="hidden" name="index" value="${counter.count}" />
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                    <c:if test="${not empty result[counter.count + 1]}">
                                        <div class="row-item">
                                            <div class="item">
                                                <div class="image-box">
                                                    <img class="item-img" src=${result[counter.count + 1].img}/> 
                                                </div>
                                                <div class="item-price">$ ${result[counter.count + 1].rentPrice}<br/>
                                                    <div class="item-location">
                                                        <img src="css/icon_previous_location.png"/>
                                                        <span>${result[counter.count + 1].distance} km</span>
                                                    </div>
                                                    <div class="item-market">
                                                        Coop-mart: <span>${result[counter.count + 1].marketDistance} km</span>
                                                    </div>
                                                </div>

                                                <div class="item-bonus">
                                                    <c:if test="${result[counter.count + 1].bonusDTO.washing}">
                                                        <div class="bonus">
                                                            <img src="css/icons_washing_machine.png"/><br/>
                                                            Máy giặt
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${result[counter.count + 1].bonusDTO.fridge}">
                                                        <div class="bonus">
                                                            <img src="css/icons_fridge.png"/><br/>
                                                            Tủ lạnh
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${result[counter.count + 1].bonusDTO.air_conditioner}">
                                                        <div class="bonus">
                                                            <img src="css/icons_air_conditioner.png"/><br/>
                                                            Điều hòa
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${result[counter.count + 1].bonusDTO.parking}">
                                                        <div class="bonus">
                                                            <img src="css/icons_scooter.png"/><br/>
                                                            Đậu xe
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${result[counter.count + 1].bonusDTO.heater}">
                                                        <div class="bonus">
                                                            <img src="css/icons_shower.png"/><br/>
                                                            Bình nóng lạnh
                                                        </div>
                                                    </c:if>
                                                </div>

                                                <div class="action">
                                                    <a href=${result[counter.count + 1].linkNew} target="_blank"><input type="submit" value="Website" name="action" class="btnBrowse"/></a>
                                                    <form action="ProcessServlet" method="POST">
                                                        <input type="submit" value="love" name="action" class="love"/>
                                                        <input type="hidden" name="index" value="${counter.count+1}" />
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                    <c:if test="${not empty result[counter.count + 2]}">
                                        <div class="row-item">
                                            <div class="item">
                                                <div class="image-box">
                                                    <img class="item-img" src=${result[counter.count + 2].img}/> 
                                                </div>
                                                <div class="item-price">$ ${result[counter.count + 2].rentPrice}<br/>
                                                    <div class="item-location">
                                                        <img src="css/icon_previous_location.png"/>
                                                        <span>${result[counter.count + 2].distance} km</span>
                                                    </div>
                                                    <div class="item-market">
                                                        Coop-mart: <span>${result[counter.count + 2].marketDistance} km</span>
                                                    </div>
                                                </div>

                                                <div class="item-bonus">
                                                    <c:if test="${result[counter.count + 2].bonusDTO.washing}">
                                                        <div class="bonus">
                                                            <img src="css/icons_washing_machine.png"/><br/>
                                                            Máy giặt
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${result[counter.count + 2].bonusDTO.fridge}">
                                                        <div class="bonus">
                                                            <img src="css/icons_fridge.png"/><br/>
                                                            Tủ lạnh
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${result[counter.count + 2].bonusDTO.air_conditioner}">
                                                        <div class="bonus">
                                                            <img src="css/icons_air_conditioner.png"/><br/>
                                                            Điều hòa
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${result[counter.count + 2].bonusDTO.parking}">
                                                        <div class="bonus">
                                                            <img src="css/icons_scooter.png"/><br/>
                                                            Đậu xe
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${result[counter.count + 2].bonusDTO.heater}">
                                                        <div class="bonus">
                                                            <img src="css/icons_shower.png"/><br/>
                                                            Bình nóng lạnh
                                                        </div>
                                                    </c:if>
                                                </div>

                                                <div class="action">
                                                    <a href=${result[counter.count + 2].linkNew} target="_blank"><input type="submit" value="Website" name="action" class="btnBrowse"/></a>
                                                    <form action="ProcessServlet" method="POST">
                                                        <input type="submit" value="love" name="action" class="love"/>
                                                        <input type="hidden" name="index" value="${counter.count+2}" />
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>

                </c:if>
            </div>
        </c:if>
        <div id="map"></div>
        <!--API key google map-->
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDcw-KnCAPzni3QYvgk3RDoTZL65xh8a4o&libraries=places&language=vi&region=VI&callback=initAutocomplete"
        async defer></script>
    </body>
</html>

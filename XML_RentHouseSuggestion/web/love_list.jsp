<%-- 
    Document   : love_list
    Created on : Mar 24, 2020, 3:16:36 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/default.css"/>
        <link rel="stylesheet" href="css/love-list.css"/>
        <link href="https://fonts.googleapis.com/css?family=Muli&display=swap" rel="stylesheet">

        <title>Danh sách ưu thích</title>
    </head>
    <body>
        <div class="header_container">
            <div class="logo" onclick="location.href = ''"></div>
            <div class="line">
            </div>
            <form action="ProcessServlet" method="POST">
                <input type="submit" value="View love list" name="action" class="love_list"/>
            </form>

            <div class="login" onclick="location.href = 'loginPage.html'"></div>
        </div>
        <div class="cart">
            <div class="cart-item">
                <span class="item-title">Ký túc xá homestay quận 9</span>
                <div class="action">
                <a href=${top.linkNew} target="_blank"><input type="submit" value="Website" name="action" class="btnBrowse"/></a>
                </div>
            </div>
            <div class="cart-item">

            </div>
            <div class="cart-item">

            </div>
            <div class="cart-item">

            </div>
        </div>
    </body>
</html>

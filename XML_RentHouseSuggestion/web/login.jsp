<%-- 
    Document   : login
    Created on : Mar 22, 2020, 12:08:31 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Đăng nhập</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/default.css"/>
        <link href="https://fonts.googleapis.com/css?family=Vollkorn&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Frank+Ruhl+Libre&display=swap" rel="stylesheet">
    </head>
    <body>
        <!--        header-->
        <div class="header_container">
            <div class="logo" onclick="location.href = ''"></div>
            <div class="line">
            </div>
            <div class="love_list"></div>
            <div class="login" onclick="location.href = 'loginPage.html'"></div>
        </div>

        <!--login space-->
        <div id="login_space">
            <div class="login-first">ĐĂNG NHẬP</div>
            <div class="login-second">
                <form action="ProcessServlet" method="POST">
                    Username<br/>
                    <input type="text" name="txtUsername" value="" /><br/>
                    Password<br/>
                    <input type="password" name="txtPassword" value="" /><br/>

                    <input type="submit" value="Đăng nhập vào tài khoản của bạn" name="action" class="button-login"/>
                    <c:if test="${requestScope.CHECK_LOGIN == false}">
                        <h4>Tài khoản đăng nhập không đúng!</h4>
                    </c:if>
                </form>
            </div>
        </div>
    </body>
</html>

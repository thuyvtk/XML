<%-- 
    Document   : admin
    Created on : Mar 20, 2020, 3:01:03 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang của Admin</title>
        <link rel="stylesheet" href="css/admin.css"/>
    </head>
    <body>
        <div class="header">
            <div class="wellcome">
                ${sessionScope.USERNAME}
            </div>
            <form action="ProcessServlet">
                <input type="submit" value="ĐĂNG XUẤT" class="btnLogin" name="action"/>
            </form>
        </div>

        <div class="content">
            <div class="admin">
                <h1>Administrator</h1>
                <!--<input type="submit" value="User's page" name="action" class="btnUserPage"/>-->
            </div>
            <div class="crawlAction">
                <div class="title">
                    Cập nhật dữ liệu
                </div>
                <div class="crawlNews">
                    Tin cho thuê nhà<br/>
                    <form action="ProcessServlet" method="POST">
                        <input type="submit" value="Cập nhật dữ liệu" name="action" class="btnUpdate"/>
                    </form>
                </div>
                <div class="crawlBonus">
                    Tiện ích<br/>
<!--                    <form action="ProcessServlet" method="POST">
                        <input type="submit" value="bachhoaxanh" class="bachhoaxanh" name="action"/>
                    </form>
                    -->
                    <form action="ProcessServlet" method="POST">
                        <input type="submit" value="market" class="coopmart" name="action"/>
                    </form>

                    <div class="school">
                    </div>
                </div>
            </div>
            <div class="bonus"></div>
        </div>
    </body>
</html>

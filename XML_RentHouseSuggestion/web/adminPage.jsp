<%-- 
    Document   : admin.jsp
    Created on : Mar 20, 2020, 1:44:36 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Administrator page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div>
            <form action="ProcessServlet" method="POST">
                <input type="submit" value="crawl" name="action"/>
            </form>
        </div>
    </body>
</html>

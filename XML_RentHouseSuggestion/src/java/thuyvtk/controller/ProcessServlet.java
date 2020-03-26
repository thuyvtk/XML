/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuyvtk.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ASUS
 */
public class ProcessServlet extends HttpServlet {
    
//    private final String INDEX_PAGE = "admin.jsp";
    private final String INDEX_PAGE = "indexSearch.jsp";
//    private final String INDEX_PAGE = "home.jsp";
    private final String CRAWL = "CrawlServlet";
    private final String CRAWL_BACHHOAXANH_SERVLET = "CrawlBachhoaxanhServlet";
    private final String CRAWL_MARKET_SERVLET = "CrawlMarkethServlet";
    private final String CRAWL_SCHOOL_SERVLET = "CrawlSchoolServlet";
    private final String SEARCH_SERVLET = "SearchServlet";
    private final String LOGIN_SERVLET = "LoginServlet";
    private final String LOGOUT_SERVLET = "LogoutServlet";
    private final String SEARCH_CLIENT_SERVLET = "SearchClientServlet";
    private final String ADD_TO_LOVELIST_SERVLET = "AddToLoveListServlet";
    private final String VIEW_LOVELIST_SERVLET = "ViewLoveListServlet";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = SEARCH_CLIENT_SERVLET;
        try {
            request.setCharacterEncoding("UTF-8");
            String action = request.getParameter("action");
//            action = URLEncoder.encode(action, "UTF-8");
            if (action == null || action.equals("")) {
            } else {
                switch (action) {
                    case "Cập nhật dữ liệu":
                        url = CRAWL;
                        break;
                    case "bachhoaxanh":
                        url = CRAWL_BACHHOAXANH_SERVLET;
                        break;
                    case "market":
                        url = CRAWL_MARKET_SERVLET;
                        break;
                    case "school":
                        url = CRAWL_SCHOOL_SERVLET;
                        break;
                    case "Search":
                        url = SEARCH_SERVLET;
                        break;
                    case "Search ":
                        url = SEARCH_CLIENT_SERVLET;
                        break;
                    case "Đăng nhập vào tài khoản của bạn":
                        url = LOGIN_SERVLET;
                        break;
                    case "ĐĂNG XUẤT":
                        url = LOGOUT_SERVLET;
                        break;
                    case "love":
                        url = ADD_TO_LOVELIST_SERVLET;
                        break;
                    case "View love list":
                        url = VIEW_LOVELIST_SERVLET;
                        break;
                }
            }
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

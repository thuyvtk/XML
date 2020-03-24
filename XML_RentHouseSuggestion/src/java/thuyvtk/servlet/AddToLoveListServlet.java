/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuyvtk.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import thuyvtk.cart.LoveListBean;
import thuyvtk.dto.HouseDTO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "AddToLoveListServlet", urlPatterns = {"/AddToLoveListServlet"})
public class AddToLoveListServlet extends HttpServlet {

    private final String INDEX_PAGE = "ProcessServlet";

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
        PrintWriter out = response.getWriter();
        String url = INDEX_PAGE;
        try {
            HttpSession session = request.getSession(true);
            
            String latitude = (String) session.getAttribute("LATITUDE");
            String longitude = (String) session.getAttribute("LONGITUDE");
            LoveListBean loveListBean = (LoveListBean) session.getAttribute("LOVE_LIST");
            
            if (loveListBean == null) {
                loveListBean = new LoveListBean();
            } 
            int index = Integer.parseInt(request.getParameter("index"));
            
            List<HouseDTO> result = (List<HouseDTO>) session.getAttribute("LIST_HOUSE");
            HouseDTO loveItem = result.get(index);
            
            boolean exist = loveListBean.addRentNew(loveItem);
            if (exist) {
                request.setAttribute("check_exist", exist);
            }
            session.setAttribute("LOVE_LIST", loveListBean);
            
            url = "ProcessServlet?action=Search&latitude="+latitude+"&longitude="+longitude;
            
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
            out.close();
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuyvtk.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import thuyvtk.dao.HouseDAO;
import thuyvtk.dto.BonusDTO;
import thuyvtk.dto.HouseDTO;
import thuyvtk.jaxbObject.HouseItem;
import thuyvtk.jaxbObject.ListHouse;
import thuyvtk.utilities.Utilites;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    private final String INDEX_PAGE = "indexSearch.jsp";

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

            String searchWay = request.getParameter("searchWay");

            if (searchWay.equals("searchLike")) {
                HouseDAO dao = new HouseDAO();
                String searchValue = request.getParameter("txtSearchValue");
                ListHouse searchedList;
                if (searchValue != null) {
                    searchedList = dao.searchLikeAddress(searchValue);
                    request.setAttribute("LISTHOUSE", searchedList.getHouse());
                }
                // get top 100 house by time desc       
                ListHouse houses = dao.findLasted100House();
                String str = Utilites.marshallToString(houses);
                request.setAttribute("INFO", str);
            }

            if (searchWay.equals("searchByLocation")) {
                String latitude = request.getParameter("latitude");
                String longitude = request.getParameter("longitude");

                if (!latitude.isEmpty() && !longitude.isEmpty()) {
                    int distance = Integer.parseInt(request.getParameter("dropdownDistance"));
                    String cbSize = request.getParameter("cbSize");
                    boolean checkSize = false;
                    if (cbSize != null && cbSize.equals("true")) {
                        checkSize = true;
                    }

                    BonusDTO requireBonus = new BonusDTO(false, false, false, false, false);
                    String cbFridge = request.getParameter("cbFridge");
                    if (cbFridge != null && cbFridge.equals("ON")) {
                        requireBonus.setFridge(true);
                    }
                    String cbAir = request.getParameter("cbAir");
                    if (cbAir != null & cbAir.equals("ON")) {
                        requireBonus.setAir_conditioner(true);
                    }
                    String cbWashing = request.getParameter("cbWashing");
                    if (cbWashing != null && cbWashing.equals("ON")) {
                        requireBonus.setWashing(true);
                    }
                    String cbParking = request.getParameter("cbParking");
                    if (cbParking != null && cbParking.equals("ON")) {
                        requireBonus.setParking(true);
                    }
                    String cbShower = request.getParameter("cbShower");
                    if (cbShower.equals("ON")) {
                        requireBonus.setHeater(true);
                    }
                    HttpSession session = request.getSession();
                    session.setAttribute("LATITUDE", latitude);
                    session.setAttribute("LONGITUDE", longitude);
                    HouseDAO houseDAO = new HouseDAO();
                    houseDAO.findHouses(latitude, longitude, distance);

                    float maxSize = houseDAO.getMaxSize();

                    List<HouseDTO> listHouses = houseDAO.getListHouses();
                    for (HouseDTO item : listHouses) {
                        item.setRequestDistance(distance);
                        item.setCheckSize(checkSize);
                        item.setRequireBonus(requireBonus);
                        item.setMaxSize(maxSize);
                    }

                    Collections.sort(listHouses);
                    request.setAttribute("LIST_HOUSE", listHouses);
                    session.setAttribute("LIST_HOUSE", listHouses);
                    List<HouseItem> top4 = new ArrayList<>();
                    for (int i = 0; i < 3; i++) {
                        top4.add(listHouses.get(i));
                    }
                    request.setAttribute("TOP4", top4);
                }

            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
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

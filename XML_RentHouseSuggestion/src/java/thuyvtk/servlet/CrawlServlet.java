/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuyvtk.servlet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import thuyvtk.common.Constraint;
import thuyvtk.crawler.Crawler;
import thuyvtk.utilities.XMLHelper;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "CrawlServlet", urlPatterns = {"/CrawlServlet"})
public class CrawlServlet extends HttpServlet {

    private final String ADMIN = "adminPage.html";

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

        String url = ADMIN;
        try {
            String realPath = request.getServletContext().getRealPath("/");
            DOMResult dom = Crawler.returnXMLResult(realPath + Constraint.PHONGTOT_XML_CONFIG, realPath + Constraint.PHONGTOT_XSLT);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            StreamResult streamResult = new StreamResult(new FileOutputStream(realPath + Constraint.PHONGTOT_XML_OUTPUT));
            transformer.transform(new DOMSource(dom.getNode()), streamResult);
            XMLHelper xmlHelper = new XMLHelper();
            xmlHelper.JAXBUnmarshallingHouse(realPath + Constraint.PHONGTOT_XML_OUTPUT);

        } catch (FileNotFoundException | TransformerException ex) {
            Logger.getLogger(CrawlServlet.class.getName()).log(Level.SEVERE, "CrawlServler:", ex.getMessage());
        } catch (NamingException ex) {
            Logger.getLogger(CrawlServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CrawlServlet.class.getName()).log(Level.SEVERE, null, ex);
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

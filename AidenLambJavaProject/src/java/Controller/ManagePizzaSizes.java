/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Entities.PizzaSize;
import DataAccess.PizzaSizeDAO;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author aiden
 */
@WebServlet(name = "ManagePizzaSizes", urlPatterns = {"/ManagePizzaSizes/pizzaSizes"})
public class ManagePizzaSizes extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
      private PizzaSizeDAO getDAO(HttpServletRequest request) {
        // one dao object per session
        HttpSession session = request.getSession();
        PizzaSizeDAO dao = (PizzaSizeDAO) session.getAttribute("pizzaSizeDAO");
        if (dao == null) {
            dao = new PizzaSizeDAO();
            session.setAttribute("pizzaSizeDAO", dao);
        }
        return dao;
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
        PizzaSizeDAO dao = getDAO(request);
        try (PrintWriter out = response.getWriter()) {
            Gson g = new Gson();
            List<PizzaSize> result = dao.getAll();
            if (result == null || dao.getError() != null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println(g.toJson("ERROR: " + dao.getError()));
            } else {
                System.out.println("controller.ManagePizzaSizes.doGet(), n=" + result.size());
                out.println(g.toJson(result));
            }
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

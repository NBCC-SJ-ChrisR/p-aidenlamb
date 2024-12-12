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
import Entities.PizzaCrust;
import DataAccess.PizzaCrustDAO;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author aiden
 */
@WebServlet(name = "ManagePizzaCrust", urlPatterns = {"/ManagePizzaCrust/pizzaCrust"})
public class ManagePizzaCrust extends HttpServlet {

    private PizzaCrustDAO getDAO(HttpServletRequest request) {
        // one dao object per session
        HttpSession session = request.getSession();
        PizzaCrustDAO dao = (PizzaCrustDAO) session.getAttribute("pizzaCrustDAO");
        if (dao == null) {
            dao = new PizzaCrustDAO();
            session.setAttribute("pizzaCrustDAO", dao);
        }
        return dao;
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PizzaCrustDAO dao = getDAO(request);
        try (PrintWriter out = response.getWriter()) {
            Gson g = new Gson();
            List<PizzaCrust> result = dao.getAll();
            if (result == null || dao.getError() != null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println(g.toJson("ERROR: " + dao.getError()));
            } else {
                System.out.println("controller.ManagePizzaCrust.doGet(), n=" + result.size());
                out.println(g.toJson(result));
            }
        }
    }

 
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

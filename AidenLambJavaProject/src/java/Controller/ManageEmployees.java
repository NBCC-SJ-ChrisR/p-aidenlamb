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
import Entities.Employee;
import DataAccess.EmployeeDAO;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author aiden
 */
@WebServlet(name = "ManageEmployees", urlPatterns = {"/ManageEmployees/employees/*"})
public class ManageEmployees extends HttpServlet {

   
    private EmployeeDAO getEmployeeDAO(HttpServletRequest request) {
        // one dao object per session
        HttpSession session = request.getSession();
        EmployeeDAO dao = (EmployeeDAO) session.getAttribute("employeeDAO");
        if (dao == null) {
            dao = new EmployeeDAO();
            session.setAttribute("employeeDAO", dao);
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
        EmployeeDAO dao = getEmployeeDAO(request);
        String username = request.getPathInfo().substring(1);
        try (PrintWriter out = response.getWriter()) {
            Gson g = new Gson();
            Employee result = dao.findByUsername(username);
            if (result == null || dao.getError() != null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println(g.toJson("ERROR: " + dao.getError()));
            } else {
                System.out.println("controller.Employee.doGet(), n=" + result.getEmployee_id());
                out.println(g.toJson(result));
                Authenticator au = new Authenticator();
                au.setEmployeeID(request, result.getEmployee_id());
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    

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

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
import Entities.PizzaTopping;
import DataAccess.PizzaToppingDAO;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author aiden
 */
@WebServlet(name = "ManagePizzaToppings", urlPatterns = {"/ManagePizzaToppings/pizzaToppings", "/ManagePizzaToppings/pizzaToppings/*"})
public class ManagePizzaToppings extends HttpServlet {

    private PizzaToppingDAO getDAO(HttpServletRequest request) {
        // one dao object per session
        HttpSession session = request.getSession();
        PizzaToppingDAO dao = (PizzaToppingDAO) session.getAttribute("pizzaToppingDAO");
        if (dao == null) {
            dao = new PizzaToppingDAO();
            session.setAttribute("pizzaToppingDAO", dao);
        }
        return dao;
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PizzaToppingDAO dao = getDAO(request);
        try (PrintWriter out = response.getWriter()) {
            Gson g = new Gson();
            List<PizzaTopping> result = dao.getAll();
            if (result == null || dao.getError() != null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println(g.toJson("ERROR: " + dao.getError()));
            } else {
                System.out.println("controller.ManagePizzaToppings.doGet(), n=" + result.size());
                out.println(g.toJson(result));
            }
        }
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PizzaToppingDAO dao = getDAO(request);
        try (PrintWriter out = response.getWriter()) {
            Scanner sc = new Scanner(request.getReader());
            String jsonData = sc.nextLine();
            Gson g = new Gson();
            PizzaTopping topping = g.fromJson(jsonData, PizzaTopping.class);
            boolean success = dao.insert(topping);
            if (!success || dao.getError() != null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println(g.toJson("ERROR: " + dao.getError()));
            } else {
                out.println(success);
            }
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PizzaToppingDAO dao = getDAO(request);
        try (PrintWriter out = response.getWriter()) {
            Scanner sc = new Scanner(request.getReader());
            String jsonData = sc.nextLine();
            Gson g = new Gson();
            PizzaTopping topping = g.fromJson(jsonData, PizzaTopping.class);
            boolean success = dao.update(topping);
            if (!success || dao.getError() != null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println(g.toJson("ERROR: " + dao.getError()));
            } else {
                out.println(success);
            }
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PizzaToppingDAO dao = getDAO(request);
        try (PrintWriter out = response.getWriter()) {
            int id = Integer.parseInt(request.getPathInfo().substring(1));
            boolean success = dao.delete(id);
            Gson g = new Gson();
            if (!success || dao.getError() != null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println(g.toJson("ERROR: " + dao.getError()));
            } else {
                out.println(success);
            }
        }
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

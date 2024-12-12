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
import Entities.PizzaTopping_Map;
import DataAccess.PizzaTopping_MapDAO;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author aiden
 */
@WebServlet(name = "ManagePizzaTopping_Maps", urlPatterns = {"/ManagePizzaTopping_Maps/pizzaTopping_Maps", "/ManagePizzaTopping_Maps/pizzaTopping_Maps/*"})
public class ManagePizzaTopping_Maps extends HttpServlet {

    private PizzaTopping_MapDAO getDAO(HttpServletRequest request) {
        // one dao object per session
        HttpSession session = request.getSession();
        PizzaTopping_MapDAO dao = (PizzaTopping_MapDAO) session.getAttribute("pizzaTopping_MapDAO");
        if (dao == null) {
            dao = new PizzaTopping_MapDAO();
            session.setAttribute("pizzaTopping_MapDAO", dao);
        }
        return dao;
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PizzaTopping_MapDAO dao = getDAO(request);
        try (PrintWriter out = response.getWriter()) {
            Gson g = new Gson();
            List<PizzaTopping_Map> result = dao.getAll();
            if (result == null || dao.getError() != null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println(g.toJson("ERROR: " + dao.getError()));
            } else {
                System.out.println("controller.ManagePizzaToppings_Map.doGet(), n=" + result.size());
                out.println(g.toJson(result));
            }
        }
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PizzaTopping_MapDAO dao = getDAO(request);
        try (PrintWriter out = response.getWriter()) {
            Scanner sc = new Scanner(request.getReader());
            String jsonData = sc.nextLine();
            Gson g = new Gson();
            PizzaTopping_Map map = g.fromJson(jsonData, PizzaTopping_Map.class);
            boolean success = dao.insert(map);
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
        PizzaTopping_MapDAO dao = getDAO(request);
        try (PrintWriter out = response.getWriter()) {
            Scanner sc = new Scanner(request.getReader());
            String jsonData = sc.nextLine();
            Gson g = new Gson();
            PizzaTopping_Map map = g.fromJson(jsonData, PizzaTopping_Map.class);
            boolean success = dao.update(map);
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

        PizzaTopping_MapDAO dao = getDAO(request);
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

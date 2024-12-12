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
import Entities.Order;
import DataAccess.OrderDAO;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author aiden
 */
@WebServlet(name = "ManageOrders", urlPatterns = {"/ManageOrders/orders", "/ManageOrders/orders/*"})
public class ManageOrders extends HttpServlet {

   private OrderDAO getDAO(HttpServletRequest request) {
        // one dao object per session
        HttpSession session = request.getSession();
        OrderDAO dao = (OrderDAO) session.getAttribute("orderDAO");
        if (dao == null) {
            dao = new OrderDAO();
            session.setAttribute("orderDAO", dao);
        }
        return dao;
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            
        OrderDAO dao = getDAO(request);
        try (PrintWriter out = response.getWriter()) {
            Gson g = new Gson();
            try {
            List<Order> result = dao.getAll();
            if (result == null || dao.getError() != null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println(g.toJson("ERROR: " + dao.getError()));
            } else {
                System.out.println("controller.ManageOrders.doGet(), n=" + result.size());
                out.println(g.toJson(result));
            }
        }
        
        catch(Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println(g.toJson("ERROR: " + dao.getError()));
        } 
        }
        
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OrderDAO dao = getDAO(request);
        try (PrintWriter out = response.getWriter()) {
            Gson g = new Gson();
            try {
            Scanner sc = new Scanner(request.getReader());
            String jsonData = sc.nextLine();
            
            Order order = g.fromJson(jsonData, Order.class);
            boolean success = dao.insert(order);
            if (!success || dao.getError() != null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println(g.toJson("ERROR: " + dao.getError()));
            } else {
                out.println(success);
            }
            }
            catch(Exception ex) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println(g.toJson("ERROR: " + dao.getError()));
            }
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OrderDAO dao = getDAO(request);
        try (PrintWriter out = response.getWriter()) {
            Gson g = new Gson();
            try {
            Scanner sc = new Scanner(request.getReader());
            String jsonData = sc.nextLine();
            Order orderFromPayload = g.fromJson(jsonData, Order.class);
            if(orderFromPayload == null) {
                return;
            }
            Order orderFromDB = dao.find(orderFromPayload.getOrder_id());
            if(orderFromDB == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println(g.toJson("ERROR 1: " + dao.getError()));
                return;
            }
            orderFromDB.setOrderStatus(orderFromPayload.getOrderStatus());
            boolean success = dao.update(orderFromDB);
            if (!success || dao.getError() != null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println(g.toJson("ERROR 2: " + dao.getError()));
            } else {
                out.println(success);
            }
            }
            catch(Exception ex) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println(g.toJson("ERROR 3: " + ex.getMessage()));
            }
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        OrderDAO dao = getDAO(request);
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

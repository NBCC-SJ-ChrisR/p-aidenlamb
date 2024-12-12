/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccess;

/**
 *
 * @author aiden
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import Entities.Pizza;

public class PizzaDAO {
    private Connection conn = null;
    private PreparedStatement selectAllStatement = null;
    private PreparedStatement insertStatement = null;
    private PreparedStatement updateStatement = null;
    private PreparedStatement deleteStatement = null;
    
    String lastError = null;

    private boolean init() {
        lastError = null;
        if (conn != null) {
            return true;
        }
        conn = ConnectionManager.getConnection(ConnectionParameters.URL, ConnectionParameters.USERNAME, ConnectionParameters.PASSWORD);
        if (conn != null)            
            try {
            selectAllStatement = conn.prepareStatement("SELECT * FROM pizza");
            insertStatement = conn.prepareStatement("INSERT INTO pizza (order_id, pizzaCrust_id, pizzaSize_id, priceEach, quantity, totalPrice) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            updateStatement = conn.prepareStatement("UPDATE pizza SET order_id=?, pizzaCrust_id=?, pizzaSize_id=?, priceEach=?, quantity=?, totalPrice=? WHERE pizza_id=?");
            deleteStatement = conn.prepareStatement("DELETE FROM pizza WHERE pizza_id=?");
            return true;
        } catch (SQLException ex) {
            lastError += ("************************");
            lastError += ("\n** Error preparing SQL");
            lastError += ("\n** " + ex.getMessage());
            lastError += ("\n************************");
            conn = null;
        }
        return false;
    }
    
    public List<Pizza> getAll() {
        lastError = null;
        List<Pizza> list = new ArrayList();
        if (!init()) {
            return list;
        }

        ResultSet rs;
        try {
            rs = selectAllStatement.executeQuery();
        } catch (SQLException ex) {
            lastError += ("************************");
            lastError += ("\n** Error retreiving Pizzas");
            lastError += ("\n** " + ex.getMessage());
            lastError += ("\n************************");
            return list;
        }

        try {
            while (rs.next()) {
                int order_id = rs.getInt("order_id");
                int pizzaCrust_id = rs.getInt("pizzaCrust_id");
                int pizzaSize_id = rs.getInt("pizzaSize_id");
                int pizza_id = rs.getInt("pizza_id");
                double priceEach = rs.getDouble("priceEach");
                int quantity = rs.getInt("quantity");
                double totalPrice = rs.getDouble("totalPrice");
                
                list.add(new Pizza(order_id, pizzaCrust_id, pizzaSize_id, pizza_id, priceEach, quantity, totalPrice));
            }
            System.err.println("*** getAll() - found " + list.size() + " pizzas");

        } catch (SQLException ex) {
            lastError += ("\n************************");
            lastError += ("\n** Error populating Pizzas");
            lastError += ("\n** " + ex.getMessage());
            lastError += ("\n************************");
        }
        return list;
    }
    public boolean insert(Pizza p) {
        lastError = null;
        if (!init()) {
            return false;
        }
        System.err.println("inserting ...");
        ResultSet rs = null;
        try {
            insertStatement.setInt(1, p.getOrder_id());
            insertStatement.setInt(2, p.getPizzaCrust_id());
            insertStatement.setInt(3, p.getPizzaSize_id());
            insertStatement.setDouble(4, p.getPriceEach());
            insertStatement.setInt(5, p.getQuantity());
            insertStatement.setDouble(6, p.getTotalPrice());
            int rowCount = insertStatement.executeUpdate();
            if (rowCount != 1) {
                lastError = "An Error Occured while creating the Pizza.";
                return false;
            }

            rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                p.setPizza_id(rs.getInt(1));
                System.out.println("DataAccess.PizzaDAO.insert() id=" + p.getPizza_id());
            }

            return true;
        } catch (SQLException ex) {
            lastError += ("************************");
            lastError += ("\n** Error inserting pizza");
            lastError += ("\n**" + ex.getMessage());
            lastError += ("\n************************");
            return false;
        } finally {
            if (rs != null)
                 try {
                rs.close();
            } catch (SQLException ex) {
                // SQUELCH
            }
        }
    }
    
    public boolean update(Pizza p) {
        lastError = null;
        try {
            updateStatement.setInt(1, p.getOrder_id());
            updateStatement.setInt(2, p.getPizzaCrust_id());
            updateStatement.setInt(3, p.getPizzaSize_id());
            updateStatement.setDouble(4, p.getPriceEach());
            updateStatement.setInt(5, p.getQuantity());
            updateStatement.setDouble(6, p.getTotalPrice());
            
            int rowCount = updateStatement.executeUpdate();
            
            if (rowCount != 1) {
                System.out.println("DataAccess.PizzaDAO.update() rows=" + rowCount);
                return false;
            }
            
            return true;
        } catch (SQLException ex) {
            lastError += ("************************");
            lastError += ("\n** Error updating Pizza");
            lastError += ("\n**" + ex.getMessage());
            lastError += ("\n************************");
            return false;
        } 
    }
    
     public boolean delete(int id) {
        lastError = null;
        try {
            deleteStatement.setInt(1, id);
            int rowCount = deleteStatement.executeUpdate();
            if (rowCount != 1) {
                System.out.println("DataAccess.PizzaDAO.delete() rows=" + rowCount);
                return false;
            }

            return true;
        } catch (SQLException ex) {
            lastError += ("************************");
            lastError += ("\n** Error deleting Pizza");
            lastError += ("\n**" + ex.getMessage());
            lastError += ("\n************************");
            return false;
        } 
    }
    
    public String getError() {
        return lastError;
    }
}

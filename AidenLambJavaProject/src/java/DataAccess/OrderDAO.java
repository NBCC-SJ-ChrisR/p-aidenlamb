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
import java.sql.SQLException;
import Entities.Order;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import java.sql.Timestamp;

public class OrderDAO {
    private Connection conn = null;
    private PreparedStatement selectAllStatement = null;
    private PreparedStatement insertStatement = null;
    private PreparedStatement updateStatement = null;
    private PreparedStatement deleteStatement = null;
    private PreparedStatement findStatement = null;
    String lastError = null;

    private boolean init() {
        lastError = null;
        if (conn != null) {
            return true;
        }
        conn = ConnectionManager.getConnection(ConnectionParameters.URL, ConnectionParameters.USERNAME, ConnectionParameters.PASSWORD);
        if (conn != null)            
            try {
            selectAllStatement = conn.prepareStatement("SELECT * FROM orders");
            findStatement = conn.prepareStatement("SELECT * FROM orders where order_id=?");
            insertStatement = conn.prepareStatement("INSERT INTO orders (amountPaid, customer_id, delivery, hst, orderStatus, orderTotal, subTotal) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            //Update String set to only update orderStatus, normally this would be done with a seperate dao,
            //however, this is the only update done to orders in this project.
            updateStatement = conn.prepareStatement("UPDATE orders SET orderStatus=? WHERE order_id=?");
            deleteStatement = conn.prepareStatement("DELETE FROM orders WHERE order_id=?");
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
    
    public List<Order> getAll() {
        lastError = null;
        List<Order> list = new ArrayList();
        if (!init()) {
            return list;
        }

        ResultSet rs;
        try {
            rs = selectAllStatement.executeQuery();
        } catch (SQLException ex) {
            lastError += ("************************");
            lastError += ("\n** Error retreiving Orders");
            lastError += ("\n** " + ex.getMessage());
            lastError += ("\n************************");
            return list;
        }

        try {
            while (rs.next()) {
                double amountPaid = rs.getDouble("amountPaid");
                int customer_id = rs.getInt("customer_id");
                int delivery = rs.getInt("delivery");
                Timestamp deliveryDate = rs.getTimestamp("deliveryDate");
                double hst = rs.getDouble("hst");
                Timestamp orderPlacedDate = rs.getTimestamp("orderPlacedDate");
                String orderStatus = rs.getString("orderStatus");
                double orderTotal = rs.getDouble("orderTotal");
                int order_id = rs.getInt("order_id");
                double subTotal = rs.getDouble("subTotal");
                list.add(new Order(amountPaid, customer_id, delivery, deliveryDate, hst, orderPlacedDate, orderStatus, orderTotal, order_id, subTotal));
            }
            System.err.println("*** getAll() - found " + list.size() + " orders");

        } catch (SQLException ex) {
            lastError += ("\n************************");
            lastError += ("\n** Error populating Orders");
            lastError += ("\n** " + ex.getMessage());
            lastError += ("\n************************");
        }
        return list;
    }
    
    public Order find(int id) {
        lastError = null;
        Order result = null;
        if (!init()) {
            return result;
        }
        ResultSet rs;
        try {
            findStatement.setInt(1, id);
            rs = findStatement.executeQuery();
        } catch (SQLException ex) {
            lastError += ("************************");
            lastError += ("\n** Error retreiving Orders");
            lastError += ("\n** " + ex.getMessage());
            lastError += ("\n************************");
            return result;
        }
        try {
            while (rs.next()) {
                double amountPaid = rs.getDouble("amountPaid");
                int customer_id = rs.getInt("customer_id");
                int delivery = rs.getInt("delivery");
                Timestamp deliveryDate = rs.getTimestamp("deliveryDate");
                double hst = rs.getDouble("hst");
                Timestamp orderPlacedDate = rs.getTimestamp("orderPlacedDate");
                String orderStatus = rs.getString("orderStatus");
                double orderTotal = rs.getDouble("orderTotal");
                int order_id = rs.getInt("order_id");
                double subTotal = rs.getDouble("subTotal");
                result = new Order(amountPaid, customer_id, delivery, deliveryDate, hst, orderPlacedDate, orderStatus, orderTotal, order_id, subTotal);
            }
            System.err.println("*** find found Order of id" + result.getOrder_id());

        } catch (SQLException ex) {
            lastError += ("\n************************");
            lastError += ("\n** Error populating Order");
            lastError += ("\n** " + ex.getMessage());
            lastError += ("\n************************");
        }
        return result;
    }
    
    public boolean insert(Order o) {
        lastError = null;
        if (!init()) {
            return false;
        }
        System.err.println("inserting ...");
        ResultSet rs = null;
        try {
            insertStatement.setDouble(1, o.getAmountPaid());
            insertStatement.setInt(2, o.getCustomer_id());
            insertStatement.setInt(3, o.getDelivery());
            insertStatement.setDouble(4, o.getHst());
            insertStatement.setString(5, o.getOrderStatus());
            insertStatement.setDouble(6, o.getOrderTotal());
            insertStatement.setDouble(7, o.getSubTotal());
            int rowCount = insertStatement.executeUpdate();
            if (rowCount != 1) {
                lastError = "An Error Occured while creating the Order.";
                return false;
            }

            rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                o.setOrder_id(rs.getInt(1));
                System.out.println("DataAccess.OrderDAO.insert() id=" + o.getOrder_id());
            }

            return true;
        } catch (SQLException ex) {
            lastError += ("************************");
            lastError += ("\n** Error inserting Employee");
            lastError += ("\n**" + ex.getMessage());
            lastError += ("\n************************");
            return false;
        } finally {
            if (rs != null) 
             try {
                rs.close();
            } catch (SQLException ex) {
            }
        }
    }
    public boolean update(Order o) {
        lastError = null;
        try {
            updateStatement.setString(1, o.getOrderStatus());
            updateStatement.setInt(2, o.getOrder_id());
            int rowCount = updateStatement.executeUpdate();
            if (rowCount != 1) {
                System.out.println("DataAccess.OrderDAO.update() rows=" + rowCount);
                lastError = "row not found";
                return false;
            }
            
            return true;
        } catch (SQLException ex) {
            lastError += ("************************");
            lastError += ("\n** Error updating Order");
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
                System.out.println("DataAccess.OrderDAO.delete() rows=" + rowCount);
                return false;
            }

            return true;
        } catch (SQLException ex) {
            lastError += ("************************");
            lastError += ("\n** Error deleting Order");
            lastError += ("\n**" + ex.getMessage());
            lastError += ("\n************************");
            return false;
        } 
    }
    
    public String getError() {
        return lastError;
    }
}

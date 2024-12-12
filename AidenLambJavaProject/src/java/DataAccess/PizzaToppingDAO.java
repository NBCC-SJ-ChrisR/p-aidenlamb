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
import java.sql.Timestamp;
import Entities.PizzaTopping;

public class PizzaToppingDAO {
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
            selectAllStatement = conn.prepareStatement("SELECT * FROM pizzatopping");
            insertStatement = conn.prepareStatement("INSERT into pizzatopping (empAddedBy, isActive, name, price) values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            updateStatement = conn.prepareStatement("UPDATE pizzatopping SET empAddedBy=? isActive=?, name=?, price=? WHERE pizzaTopping_id=?");
            deleteStatement = conn.prepareStatement("DELETE from pizzatopping where pizzaTopping_id=?");
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
    
    public List<PizzaTopping> getAll() {
        lastError = null;
        List<PizzaTopping> list = new ArrayList();
        if (!init()) {
            return list;
        }

        ResultSet rs;
        try {
            rs = selectAllStatement.executeQuery();
        } catch (SQLException ex) {
            lastError += ("************************");
            lastError += ("\n** Error retreiving Pizza Toppings");
            lastError += ("\n** " + ex.getMessage());
            lastError += ("\n************************");
            return list;
        }

        try {
            while (rs.next()) {
                Timestamp createDate = rs.getTimestamp("createDate");
                int empAddedBy = rs.getInt("empAddedBy");
                int isActive = rs.getInt("isActive");
                String name = rs.getString("name");
                int pizzaTopping_id = rs.getInt("pizzaTopping_id");
                double price = rs.getDouble("price");
                
                list.add(new PizzaTopping(createDate, empAddedBy, isActive, name, pizzaTopping_id, price));
            }
            System.err.println("*** getAll() - found " + list.size() + " pizza toppings");

        } catch (SQLException ex) {
            lastError += ("\n************************");
            lastError += ("\n** Error populating Pizza Toppings");
            lastError += ("\n** " + ex.getMessage());
            lastError += ("\n************************");
        }
        return list;
    }
    public boolean insert(PizzaTopping p) {
        lastError = null;
        if (!init()) {
            return false;
        }
        System.err.println("inserting ...");
        ResultSet rs = null;
        try {
            insertStatement.setInt(1, p.getEmpAddedBy());
            insertStatement.setInt(2, p.getIsActive());
            insertStatement.setString(3, p.getName());
            insertStatement.setDouble(4, p.getPrice());
            int rowCount = insertStatement.executeUpdate();
            if (rowCount != 1) {
                lastError = "An Error Occured while creating the Pizza Topping.";
                return false;
            }

            rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                p.setPizzaTopping_id(rs.getInt(1));
                System.out.println("DataAccess.PizzaToppingDAO.insert() id=" + p.getPizzaTopping_id());
            }

            return true;
        } catch (SQLException ex) {
            lastError += ("************************");
            lastError += ("\n** Error inserting pizza topping");
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
    
    public boolean update(PizzaTopping p) {
        lastError = null;
        try {
            updateStatement.setInt(2, p.getEmpAddedBy());
            updateStatement.setInt(3, p.getIsActive());
            updateStatement.setString(4, p.getName());
            updateStatement.setDouble(5, p.getPrice());
            
            int rowCount = updateStatement.executeUpdate();
            
            if (rowCount != 1) {
                System.out.println("DataAccess.PizzaToppingDAO.update() rows=" + rowCount);
                return false;
            }
            
            return true;
        } catch (SQLException ex) {
            lastError += ("************************");
            lastError += ("\n** Error updating Pizza Topping");
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
                System.out.println("DataAccess.PizzaToppingDAO.delete() rows=" + rowCount);
                return false;
            }

            return true;
        } catch (SQLException ex) {
            lastError += ("************************");
            lastError += ("\n** Error deleting Pizza Topping");
            lastError += ("\n**" + ex.getMessage());
            lastError += ("\n************************");
            return false;
        } 
    }
    
    public String getError() {
        return lastError;
    }
}

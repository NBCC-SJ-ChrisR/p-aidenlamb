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
import Entities.PizzaTopping_Map;
public class PizzaTopping_MapDAO {
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
            selectAllStatement = conn.prepareStatement("SELECT * FROM pizzatopping_map");
            insertStatement = conn.prepareStatement("INSERT INTO pizzatopping_map (pizzaTopping_id, pizza_id) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            updateStatement = conn.prepareStatement("UPDATE pizzatopping_map SET pizzaTopping_id=?, pizza_id=? WHERE pizzaTopping_map_id=?");
            deleteStatement = conn.prepareStatement("DELETE FROM pizzatopping_map WHERE pizzatopping_map_id=?");
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
    
    public List<PizzaTopping_Map> getAll() {
        lastError = null;
        List<PizzaTopping_Map> list = new ArrayList();
        if (!init()) {
            return list;
        }

        ResultSet rs;
        try {
            rs = selectAllStatement.executeQuery();
        } catch (SQLException ex) {
            lastError += ("************************");
            lastError += ("\n** Error retreiving Pizza Topping Maps");
            lastError += ("\n** " + ex.getMessage());
            lastError += ("\n************************");
            return list;
        }

        try {
            while (rs.next()) {
                int pizzaTopping_id = rs.getInt("pizzaTopping_id");
                int pizzaTopping_map_id = rs.getInt("pizzaTopping_map_id");
                int pizza_id = rs.getInt("pizza_id");
                
                list.add(new PizzaTopping_Map(pizzaTopping_id, pizzaTopping_map_id, pizza_id));
            }
            System.err.println("*** getAll() - found " + list.size() + " pizza topping maps");

        } catch (SQLException ex) {
            lastError += ("\n************************");
            lastError += ("\n** Error populating Pizza topping maps");
            lastError += ("\n** " + ex.getMessage());
            lastError += ("\n************************");
        }
        return list;
    }
    public boolean insert(PizzaTopping_Map p) {
        lastError = null;
        if (!init()) {
            return false;
        }
        System.err.println("inserting ...");
        ResultSet rs = null;
        try {
            insertStatement.setInt(1, p.getPizzaTopping_id());
            insertStatement.setInt(2, p.getPizza_id());
            int rowCount = insertStatement.executeUpdate();
            if (rowCount != 1) {
                lastError = "An Error Occured while creating the Pizza Topping Map.";
                return false;
            }

            rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                p.setPizza_id(rs.getInt(1));
                System.out.println("DataAccess.PizzaTopping_MapDAO.insert() id=" + p.getPizzaTopping_map_id());
            }

            return true;
        } catch (SQLException ex) {
            lastError += ("************************");
            lastError += ("\n** Error inserting pizza topping map");
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
    
    public boolean update(PizzaTopping_Map p) {
        lastError = null;
        try {
            updateStatement.setInt(1, p.getPizzaTopping_id());
            updateStatement.setInt(2, p.getPizza_id());
            
            int rowCount = updateStatement.executeUpdate();
            
            if (rowCount != 1) {
                System.out.println("DataAccess.PizzaTopping_MapDAO.update() rows=" + rowCount);
                return false;
            }
            
            return true;
        } catch (SQLException ex) {
            lastError += ("************************");
            lastError += ("\n** Error updating Pizza Topping Map");
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
                System.out.println("DataAccess.PizzaTopping_MapDAO.delete() rows=" + rowCount);
                return false;
            }

            return true;
        } catch (SQLException ex) {
            lastError += ("************************");
            lastError += ("\n** Error deleting Pizza Topping Map");
            lastError += ("\n**" + ex.getMessage());
            lastError += ("\n************************");
            return false;
        } 
    }
    
    public String getError() {
        return lastError;
    }
}

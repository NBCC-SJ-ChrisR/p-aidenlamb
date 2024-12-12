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
import Entities.PizzaCrust;

public class PizzaCrustDAO {
    private Connection conn = null;
    private PreparedStatement selectAllStatement = null;
    
    
    String lastError = null;

    private boolean init() {
        lastError = null;
        if (conn != null) {
            return true;
        }
        conn = ConnectionManager.getConnection(ConnectionParameters.URL, ConnectionParameters.USERNAME, ConnectionParameters.PASSWORD);
        if (conn != null)            
            try {
            selectAllStatement = conn.prepareStatement("SELECT * FROM pizzacrust");
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
    
    public List<PizzaCrust> getAll() {
        lastError = null;
        List<PizzaCrust> list = new ArrayList();
        if (!init()) {
            return list;
        }

        ResultSet rs;
        try {
            rs = selectAllStatement.executeQuery();
        } catch (SQLException ex) {
            lastError += ("************************");
            lastError += ("\n** Error retreiving Pizza Crusts");
            lastError += ("\n** " + ex.getMessage());
            lastError += ("\n************************");
            return list;
        }

        try {
            while (rs.next()) {
                String name = rs.getString("name");
                int pizzaCrust_id = rs.getInt("pizzaCrust_id");
                double price = rs.getDouble("price");
                
                list.add(new PizzaCrust(name, pizzaCrust_id, price));
            }
            System.err.println("*** getAll() - found " + list.size() + " pizza crusts");

        } catch (SQLException ex) {
            lastError += ("\n************************");
            lastError += ("\n** Error populating Pizza crusts");
            lastError += ("\n** " + ex.getMessage());
            lastError += ("\n************************");
        }
        return list;
    }

    
    public String getError() {
        return lastError;
    }
    
}
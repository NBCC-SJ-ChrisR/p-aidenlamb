package DataAccess;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Entities.Customer;
import java.sql.ResultSet;


public class CustomerDAO {
    private Connection conn = null;
    private PreparedStatement selectByUsernameStatement = null;
    String lastError = null;
    private boolean init() {
        lastError = null;
        if (conn != null) {
            return true;
        }
        conn = ConnectionManager.getConnection(ConnectionParameters.URL, ConnectionParameters.USERNAME, ConnectionParameters.PASSWORD);
        if (conn != null)            
            try {
            selectByUsernameStatement = conn.prepareStatement("SELECT * FROM customer where email = ?");
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
    public Customer findByEmail(String custEmail) {
        lastError = null;
        Customer c = null;
        if(!init()) {
            return c;
        }
        ResultSet rs;
        try {
            selectByUsernameStatement.setString(1, custEmail);
            rs = selectByUsernameStatement.executeQuery();
        } catch (SQLException ex) {
            lastError +=("\n************************");
            lastError +=("\n** Employee");
            lastError +=("\n** " + ex.getMessage());
            lastError +=("\n************************");
            return c;
        }
        
        try {
            while(rs.next()) {
                int id = rs.getInt("customer_id");
                String email = rs.getString("email");
                String firstName = rs.getString("firstName");
                int houseNumber = rs.getInt("houseNumber");
                String lastName = rs.getString("lastName");
                String password = rs.getString("password");
                String phoneNumber = rs.getString("phoneNumber");
                String postalCode = rs.getString("postalCode");
                String province = rs.getString("province");
                String street = rs.getString("street");
                
                c = new Customer(id, email, firstName, houseNumber, lastName, password, phoneNumber, postalCode, province, street);
            }
        } catch (SQLException ex) {
            lastError +=("\n************************");
            lastError +=("\n** Error retreiving Employee");
            lastError +=("\n** " + ex.getMessage());
            lastError +=("\n************************");
            return c;
        }
        return c;
    }
    public String getError() {
        return lastError;
    }
}


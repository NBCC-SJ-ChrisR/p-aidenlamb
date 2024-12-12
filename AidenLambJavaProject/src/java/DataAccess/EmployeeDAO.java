package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Entities.Employee;
import java.sql.ResultSet;


public class EmployeeDAO {
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
            selectByUsernameStatement = conn.prepareStatement("SELECT * FROM employee where username = ?");
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
    
    public Employee findByUsername(String empUsername) {
        lastError = null;
        Employee e = null;
        if(!init()) {
            return e;
        }
        ResultSet rs;
        try {
            selectByUsernameStatement.setString(1, empUsername);
            rs = selectByUsernameStatement.executeQuery();
        } catch (SQLException ex) {
            lastError +=("\n************************");
            lastError +=("\n** Employee");
            lastError +=("\n** " + ex.getMessage());
            lastError +=("\n************************");
            return e;
        }
        
        try {
            while(rs.next()) {
                int id = rs.getInt("employee_id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                e = new Employee(id, username, password);
            }
        } catch (SQLException ex) {
            lastError +=("\n************************");
            lastError +=("\n** Error retreiving Employee");
            lastError +=("\n** " + ex.getMessage());
            lastError +=("\n************************");
            return e;
        }
        return e;
    }
    public String getError() {
        return lastError;
    }
}



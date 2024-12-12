package DataAccess;

/**
 * It's better practice to store this information in a configuration file.
 */
public class ConnectionParameters {
    
    // use this line for MySQL
    public static final String URL = "jdbc:mysql://localhost:3306/javapizzadb?autoReconnect=false&useSSL=false&allowPublicKeyRetrieval=true";
    
    public static final String USERNAME = "JavaApp";
    public static final String PASSWORD = "JavaApp";
    
    // no instantiation allowed
    private ConnectionParameters() {}
    
} // end class ConnectionParameters

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class intialize {
    
    private static final long serialVersionUID = 1L;
    public static Connection dbConnection = null;
    public static Statement dbStatement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    public intialize() {}
    
    protected static void establishConnection() throws SQLException {
        // Establishes a connection to the database
        if (dbConnection == null || dbConnection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            dbConnection = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testdb?allowPublicKeyRetrieval=true&useSSL=false&user=john&password=yaHareemu123@");
            System.out.println(dbConnection);
        }
    }
    
    protected static void disconnect() throws SQLException {
        if (dbConnection != null && !dbConnection.isClosed()) {
            dbConnection.close();
        }
    }
    
    public static void initializeDatabase() throws SQLException, FileNotFoundException, IOException {
        try {
            establishConnection();
            
            dbStatement = dbConnection.createStatement();
            
            dbStatement.executeUpdate("SET FOREIGN_KEY_CHECKS=0");
            
            dbStatement.executeUpdate("DROP TABLE IF EXISTS Orders");
            dbStatement.executeUpdate("DROP TABLE IF EXISTS Invoices");
            dbStatement.executeUpdate("DROP TABLE IF EXISTS Customers");
            
            dbStatement.executeUpdate("SET FOREIGN_KEY_CHECKS=1");

            System.out.println("Tables removed successfully");
            
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Customers " + 
                "(customerID INT AUTO_INCREMENT PRIMARY KEY, " +
                "firstName VARCHAR(255), " +
                "lastName VARCHAR(255), " +
                "address VARCHAR(255), " +
                "creditCardInfo VARCHAR(255), " +
                "phoneNumber VARCHAR(15), " +
                "email VARCHAR(255) UNIQUE); ";
            dbStatement.executeUpdate(createTableSQL);
            
            createTableSQL = "CREATE TABLE IF NOT EXISTS Orders( " + 
                "orderID INT AUTO_INCREMENT PRIMARY KEY, " +
                "product VARCHAR(255), " +
                "quantity INT, " +
                "customerID INT, " +
                "FOREIGN KEY (customerID) REFERENCES Customers(customerID) ON DELETE CASCADE);";
            dbStatement.executeUpdate(createTableSQL);
            
            createTableSQL = "CREATE TABLE IF NOT EXISTS Invoices( " + 
                "invoiceID INT AUTO_INCREMENT PRIMARY KEY, " +
                "totalAmount DECIMAL(10, 2), " +
                "orderID INT, " +
                "FOREIGN KEY (orderID) REFERENCES Orders(orderID) ON DELETE CASCADE);";
            dbStatement.executeUpdate(createTableSQL);
    
            System.out.println("Tables created successfully");
            
            String insertSQL = "INSERT INTO Customers(firstName, lastName, address, creditCardInfo, phoneNumber, email)" +
                "VALUES ('John', 'Doe', '123 Main St', '1234-5678-9012-3456', '555-123-4567', 'john.doe@example.com')";
            dbStatement.executeUpdate(insertSQL);
            
            insertSQL = "INSERT INTO Orders(product, quantity)" +
                "VALUES ('Widget', 10)";
            dbStatement.executeUpdate(insertSQL);
            
            insertSQL = "INSERT INTO Invoices(totalAmount)" +
                "VALUES (100.00)";
            dbStatement.executeUpdate(insertSQL);

            System.out.println("Tables successfully populated with data");

        } catch(Exception e) {
            System.out.println(e);
        } finally {
            disconnect();
        }
    }
}

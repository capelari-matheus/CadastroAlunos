package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static Connection con = null;
    
    public static Connection getConnection(){
        
        if(con == null){
            createConnection();
        }
        
        return con;
        
        
    }
    

    public static void createConnection() {
        String url = "jdbc:mysql://localhost:3306/aluno";//String de conexão JDBC
        String user = "root";
        String password = "";
        try {
            con = DriverManager.getConnection(url, user, password);

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}

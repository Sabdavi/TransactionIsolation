package source;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/Test";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "saeid";

    public static Connection getConnection(boolean autoCommit) {
        Connection connection = null;
        try{
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL,USER_NAME,PASSWORD);
            connection.setAutoCommit(autoCommit);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return connection;
    }
}

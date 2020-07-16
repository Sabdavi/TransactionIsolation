package source;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Test2 {
    public static void main(String[] args) throws Exception{

        // 1. Getting database connections
        Connection connection = DbUtil.getConnection(true);
        connection.setAutoCommit(false);

        // 2. Setting repeatable reads
        connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

        List<Book> books = BookDaoUtil.books(connection);
        System.out.println("First query:" + books);

        new Thread(){

            @Override
            public void run() {
                Connection subConn = null;
                // Get the database connection
                try {
                    subConn = DbUtil.getConnection(true);
                    subConn.setAutoCommit(true);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                BookDaoUtil.save(subConn,new Book(125L,"Deep Learning","Godflow"));
            }
        }.run();

        Thread.sleep(5*1000);

        List<Book> books1 = BookDaoUtil.books(connection);
        System.out.println("Second query:" + books1);


    }
}

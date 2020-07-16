package source;

import java.sql.Connection;
import java.sql.SQLException;

public class Test1 {
    public static void main(String[] args) throws Exception{

        // 1. Getting database connections
        Connection connection = DbUtil.getConnection(true);
        connection.setAutoCommit(false);

        // 2. Setting repeatable reads
        connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

        Book book = BookDaoUtil.findById(connection, 1L);
        System.out.println("First query:" + book);

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

                BookDaoUtil.updateAuthor(subConn, "Mohsen",1L);
            }
        }.run();

        Thread.sleep(10*1000);

        Book book1 = BookDaoUtil.findById(connection, 1L);
        System.out.println("Second query:" + book1);


    }
}

package source;

import java.sql.Connection;
import java.sql.SQLException;

public class RepeatableRead {

    public void doTransactions() throws Exception{
        // 1. Getting database connections
        Connection connection = DbUtil.getConnection(false);

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
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                BookDaoUtil.updateAuthor(subConn, "Martin Fowler",1L);
            }
        }.run();

        Thread.sleep(10*1000);

        Book book1 = BookDaoUtil.findById(connection, 1L);
        System.out.println("Second query:" + book1);


    }
}

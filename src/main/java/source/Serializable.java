package source;

import java.sql.Connection;
import java.util.List;

public class Serializable {
    public void doTransactions() throws Exception{
        // 1. Getting database connections
        Connection connection = DbUtil.getConnection(false);

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
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                BookDaoUtil.save(subConn,new Book(125L,"Deep Learning","Goodfellow"));
            }
        }.run();

        Thread.sleep(10*1000);

        List<Book> books1 = BookDaoUtil.books(connection);
        System.out.println("Second query:" + books1);
    }
}

package source;

import java.sql.Connection;

public class ReadCommited {
    public void doTransactions() throws Exception {

        Connection connection = DbUtil.getConnection(false);

        // 2. Execute insert statements
        BookDaoUtil.save(connection, new Book(1L, "Deep Work", "Cal Newport"));

        // 3. Open sub-threads to simulate concurrent transactions. Set isolation level in sub-transactions
        new Thread() {

            @Override
            public void run() {
                try {
                    // Get a new database connection in the sub thread and automatically commit transactions by default
                    Connection subConn = DbUtil.getConnection(true);

                    // Setting the transaction isolation level for sub threads
                    subConn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

                    Book book = BookDaoUtil.findById(subConn, 1L);
                    System.out.println("Sub thread Query:" + book);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }.run();

        // Hibernation of main thread
        Thread.sleep(10 * 1000l);
        // Commit transaction rollback
        connection.rollback();
    }
}

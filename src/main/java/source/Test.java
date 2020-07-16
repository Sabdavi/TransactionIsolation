package source;

import java.sql.Connection;

public class Test {

    public static void main(String[] args) throws Exception {

        // to test every isolation level uncomment it

        ReadUncommited readUncommited = new ReadUncommited();
        readUncommited.doTransactions();

        /*ReadCommited readCommited = new ReadCommited();
        readCommited.doTransactions();*/

        /*BookDaoUtil.save(DbUtil.getConnection(true),new Book(1L,"Deep Work","Call Newport"));
        RepeatableRead repeatableRead = new RepeatableRead();
        repeatableRead.doTransactions();*/

        // TODO: 7/16/20 resolve lock exception
        /*Serializable serializable = new Serializable();
        serializable.doTransactions();*/
    }
}


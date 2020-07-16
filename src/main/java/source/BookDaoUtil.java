package source;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoUtil {

    // Save User objects
    public static void save(Connection connection, Book book) {

        String sql = "insert Book(id, author, title) values (?, ?, ?)";

        try {
            // Get PreparedStatement and set the backfill key
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Setting parameters
            preparedStatement.setLong(1, book.getId());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getTitle());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Query objects by id
    public static Book findById(Connection connection, Long id) {
        String sql = "select  * from Book where id = ?";

        Book book = null;
        try {
            // Get PreparedStatement and set parameters
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            // Execute the query and parse the results
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                book = new Book();
                book.setId(resultSet.getObject("id", Long.class));
                book.setAuthor(resultSet.getObject("author", String.class));
                book.setTitle(resultSet.getObject("title", String.class));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    public static List<Book> books(Connection connection){

        List<Book> books = new ArrayList<>();
        Statement statement = null;
        try{
            statement = connection.createStatement();
            String sql = "SELECT * FROM Book";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getObject("id", Long.class));
                book.setAuthor(resultSet.getObject("author", String.class));
                book.setTitle(resultSet.getObject("title", String.class));
                books.add(book);
            }

        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return books;
    }

    // Update password with id
    public static void updateAuthor(Connection connection,String author, Long id) {
        String sql = "update Book set author = ? where id = ?";

        try {

            // Get PreparedStatement and set parameters
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, author);
            preparedStatement.setLong(2, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


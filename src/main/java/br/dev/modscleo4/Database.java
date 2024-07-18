package br.dev.modscleo4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import br.dev.modscleo4.entity.Product;
import br.dev.modscleo4.marketplace.IMarketplace;

public class Database {
    private Connection connection;

    public Database(@Nonnull String path) throws SQLException {
        System.out.println("Connecting to " + path);

        this.connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", path));
        this.connection.setAutoCommit(false);
        this.initSchema();
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void initSchema() throws SQLException {
        var sql = """
            CREATE TABLE IF NOT EXISTS products (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                marketplace TEXT NOT NULL,
                name TEXT NOT NULL,
                url TEXT NOT NULL,
                price REAL NOT NULL,
                createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
        """;

        this.connection.createStatement().execute(sql);
    }

    public void truncateProducts() throws SQLException {
        var sql = "DELETE FROM products";

        this.connection.createStatement().execute(sql);
    }

    public int insertProduct(@Nonnull IMarketplace marketplace, Product product) throws SQLException {
        var sql = "INSERT INTO products (marketplace, name, url, price) VALUES (?, ?, ?, ?)";

        try {
            var statement = this.connection.prepareStatement(sql);
            statement.setString(1, marketplace.getClass().getSimpleName());
            statement.setString(2, product.name());
            statement.setString(3, product.url());
            statement.setDouble(4, product.price());
            statement.execute();

            var id = statement.getGeneratedKeys().getInt(1);

            this.connection.commit();

            return id;
        } catch (SQLException e) {
            this.connection.rollback();
            throw e;
        }
    }

    public List<Integer> insertProducts(@Nonnull IMarketplace marketplace, List<Product> products) throws SQLException {
        var sql = "INSERT INTO products (marketplace, name, url, price) VALUES (?, ?, ?, ?)";

        var ids = new ArrayList<Integer>();

        try {
            var statement = this.connection.prepareStatement(sql);
            statement.setString(1, marketplace.getClass().getSimpleName());
            for (var product : products) {
                statement.setString(2, product.name());
                statement.setString(3, product.url());
                statement.setDouble(4, product.price());
                statement.execute();

                ids.add(statement.getGeneratedKeys().getInt(1));
            }

            this.connection.commit();

            return ids;
        } catch (SQLException e) {
            this.connection.rollback();
            throw e;
        }
    }

    public Product getProduct(int id) throws SQLException {
        var sql = "SELECT * FROM products WHERE id = ?";
        var statement = this.connection.prepareStatement(sql);
        statement.setInt(1, id);
        var resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return new Product(
                resultSet.getString("name"),
                resultSet.getString("url"),
                resultSet.getDouble("price")
            );
        }

        return null;
    }

    public List<Product> getProducts(@Nonnull IMarketplace marketplace, int limit) throws SQLException {
        var sql = "SELECT * FROM products WHERE marketplace = ? ORDER BY price ASC LIMIT ?";
        var statement = this.connection.prepareStatement(sql);
        statement.setString(1, marketplace.getClass().getSimpleName());
        statement.setInt(2, limit);
        var resultSet = statement.executeQuery();

        var list = new ArrayList<Product>();
        while (resultSet.next()) {
            var product = new Product(
                resultSet.getString("name"),
                resultSet.getString("url"),
                resultSet.getDouble("price")
            );

            list.add(product);
        }

        return list;
    }

    public List<Product> getProducts(@Nonnull List<Integer> ids) throws SQLException {
        var sql = String.format("SELECT * FROM products WHERE id IN (%s)", "?".repeat(ids.size()).replace("", ", ").replaceFirst(", ", ""));
        var statement = this.connection.prepareStatement(sql);
        for (var i = 0; i < ids.size(); i++) {
            statement.setInt(i + 1, ids.get(i));
        }

        var resultSet = statement.executeQuery();

        var list = new ArrayList<Product>();
        while (resultSet.next()) {
            var product = new Product(
                resultSet.getString("name"),
                resultSet.getString("url"),
                resultSet.getDouble("price")
            );

            list.add(product);
        }

        return list;
    }

    public void close() throws SQLException {
        this.connection.close();
    }
}

package br.dev.modscleo4;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Test.None;

import br.dev.modscleo4.entity.Product;
import br.dev.modscleo4.marketplace.IMarketplace;
import br.dev.modscleo4.marketplace.MercadoLivre;

public class DatabaseTest {
    private IMarketplace marketplace = null;
    private Database db = null;

    @Before
    public void setUp() {
        try {
            marketplace = new MercadoLivre();
            db = new Database("test.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        try {
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(expected = None.class)
    public void testInitialization() throws SQLException {
        assertNotNull(marketplace);
        assertNotNull(db);
        assertNotNull(db.getConnection());

        // Check if the table exists
        var dbmd = db.getConnection().getMetaData();
        var rs = dbmd.getTables(null, null, "products", null);
        assertTrue(rs.next());
    }

    @Test(expected = None.class)
    public void testTruncateProducts() throws SQLException {
        db.truncateProducts();

        assertTrue(db.getConnection().createStatement().executeQuery("SELECT COUNT(*) FROM products").getInt(1) == 0);
        db.getConnection().commit();
    }

    @Test(expected = None.class)
    public void testInsertProduct() throws SQLException {
        var product = new Product("Test", "http://test.com", 4.44);
        var id = db.insertProduct(marketplace, product);
        assertTrue(id > 0);

        var insertedProduct = db.getProduct(id);
        assertNotNull(insertedProduct);
        assertTrue(insertedProduct.name().equals(product.name()));
        assertTrue(insertedProduct.url().equals(product.url()));
        assertTrue(insertedProduct.price() == product.price());
    }
}

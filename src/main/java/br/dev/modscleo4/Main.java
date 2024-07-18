package br.dev.modscleo4;

import br.dev.modscleo4.marketplace.CasasBahia;
import br.dev.modscleo4.marketplace.MercadoLivre;
import br.dev.modscleo4.selenium.Crawler;

/**
 * Hello world!
 *
 */
public class Main {
    public static void main(String[] args) {
        try {
            var db = new Database("products.db");
            db.truncateProducts();

            var crawler = new Crawler();
            crawler.setSearch("Xbox Series S");

            var ml = new MercadoLivre();
            crawler.setMarketplace(ml);
            db.insertProducts(crawler.getMarketplace(), crawler.start());

            var cb = new CasasBahia();
            crawler.setMarketplace(cb);
            db.insertProducts(crawler.getMarketplace(), crawler.start());

            crawler.close();

            var mlProducts = db.getProducts(ml, 3);
            System.out.println("=== Mercado Livre ===");
            mlProducts.forEach(System.out::println);

            var cbProducts = db.getProducts(cb, 3);
            System.out.println("==== Casas Bahia ====");
            cbProducts.forEach(System.out::println);

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

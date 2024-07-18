package br.dev.modscleo4.marketplace;

import java.util.List;

import org.openqa.selenium.WebDriver;

import br.dev.modscleo4.product.Product;

public interface IMarketplace {
    enum Marketplace {
        UNKNOWN,
        CASAS_BAHIA,
        MERCADO_LIVRE
    }

    /**
     * Get the name of the website
     *
     * @return The name
     */
    Marketplace get();

    /**
     * Get the search URL of the website
     *
     * @param query The query to search
     * @return The search URL
     */
    String getSearchURL(String query);

    /**
     * Crawl the website and get the products
     *
     * @param driver The WebDriver already initialized with the search URL
     * @return The products
     */
    List<Product> crawl(WebDriver driver);
}

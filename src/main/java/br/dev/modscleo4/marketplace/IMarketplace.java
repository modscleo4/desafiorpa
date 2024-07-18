package br.dev.modscleo4.marketplace;

import java.util.List;

import org.openqa.selenium.WebDriver;

import br.dev.modscleo4.entity.Product;

public interface IMarketplace {
    /**
     * Get the URL of the website
     *
     * @return The URL
     */
    String getURL();

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

package br.dev.modscleo4.selenium;

import java.util.List;
import javax.annotation.Nonnull;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import br.dev.modscleo4.entity.Product;
import br.dev.modscleo4.marketplace.IMarketplace;

public class Crawler {
    private IMarketplace marketplace;
    private String query = "";
    private WebDriver driver;

    public Crawler() {
        var options = new ChromeOptions();
        // options.addArguments("--headless=new");

        this.driver = new ChromeDriver(options);
    }

    public String getSearch() {
        return this.query;
    }

    public void setSearch(@Nonnull String query) {
        this.query = query;

        System.out.println("Searching for " + query);
    }

    public IMarketplace getMarketplace() {
        return this.marketplace;
    }

    public void setMarketplace(@Nonnull IMarketplace marketplace) {
        this.marketplace = marketplace;
    }

    public List<Product> start() {
        String url = marketplace.getSearchURL(this.query);

        System.out.println("Crawling started for " + this.marketplace.getClass().getSimpleName());
        System.out.println("URL: " + url);

        driver.get(url);
        var products = marketplace.crawl(driver);

        System.out.println("Crawling finished, found " + products.size() + " products");

        return products;
    }

    public void close() {
        driver.quit();
    }
}

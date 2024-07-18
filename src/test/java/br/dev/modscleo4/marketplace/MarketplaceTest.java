package br.dev.modscleo4.marketplace;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public abstract class MarketplaceTest<T extends IMarketplace> {
    protected WebDriver driver;
    protected T marketplace;

    protected abstract T createMarketplace();

    @Before
    public void setUp() {
        marketplace = createMarketplace();
        var driverOptions = new ChromeOptions();
        // driverOptions.addArguments("--headless");
        driver = new ChromeDriver(driverOptions);
        driver.get(marketplace.getSearchURL("Xbox Series S"));
    }

    @After
    public void tearDown() {
        driver.close();
    }

    @Test
    public void testCrawl() {
        var products = marketplace.crawl(driver);
        assertNotNull(products);
        assertTrue(products.size() > 0);
    }
}

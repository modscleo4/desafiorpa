package br.dev.modscleo4.marketplace;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.dev.modscleo4.entity.Product;

public class CasasBahia implements IMarketplace {
    @Override
    public String getURL() {
        return "https://www.casasbahia.com.br";
    }

    @Override
    public String getSearchURL(@Nonnull String query) {
        return "https://www.casasbahia.com.br/" + query.replace(" ", "-") + "/b";
    }

    @Override
    public @Nonnull List<Product> crawl(@Nonnull WebDriver driver) {
        var list = new ArrayList<Product>();

        // Wait for the search results to load
        System.out.println("Waiting for search results to load");
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> {
            var section = d.findElement(By.cssSelector("div.Item-sc-815c7bf9-0.epcKyG"));

            return section.isDisplayed();
        });

        System.out.println("Search results loaded, parsing items");
        var itemsContainer = driver.findElement(By.cssSelector("div.Item-sc-815c7bf9-0.epcKyG"));
        var items = itemsContainer.findElements(By.cssSelector("div.styles__ProductCardWrapper-sc-162770ad-3"));

        System.out.println("Found " + items.size() + " items");
        for (var item : items) {
            if (!item.isDisplayed()) {
                // Skip hidden items
                continue;
            }

            var nameAndURLElement = item.findElement(By.cssSelector("h3.product-card__title a"));
            var priceElement = item.findElement(By.cssSelector("div.product-card__highlight-price"));

            var name = nameAndURLElement.getText();
            var url = nameAndURLElement.getAttribute("href");
            var price = Double.parseDouble(priceElement.getText().replaceFirst("^[^\\$]*\\$ ?", "").replace(".", "").replace(",", "."));

            list.add(new Product(name, url, price));
        }

        return list;
    }
}

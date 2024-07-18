package br.dev.modscleo4.marketplace;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.dev.modscleo4.entity.Product;

public class MercadoLivre implements IMarketplace {
    @Override
    public String getURL() {
        return "https://www.mercadolivre.com.br";
    }

    @Override
    public String getSearchURL(@Nonnull String query) {
        return "https://lista.mercadolivre.com.br/" + query.replace(" ", "-");
    }

    @Override
    public @Nonnull List<Product> crawl(@Nonnull WebDriver driver) {
        var list = new ArrayList<Product>();

        // Wait for the search results to load
        System.out.println("Waiting for search results to load");
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> {
            var section = d.findElement(By.cssSelector("section.ui-search-results"));

            return section.isDisplayed();
        });

        System.out.println("Search results loaded, parsing items");
        var itemsContainer = driver.findElement(By.cssSelector("section.ui-search-results"));
        var items = itemsContainer.findElements(By.cssSelector("li.ui-search-layout__item"));

        System.out.println("Found " + items.size() + " items");
        for (var item : items) {
            if (!item.isDisplayed()) {
                // Skip hidden items
                continue;
            }

            var nameAndURLElement = item.findElement(By.cssSelector("a.ui-search-link__title-card"));
            var priceElement = item.findElement(By.cssSelector("span.andes-money-amount__fraction"));

            var name = nameAndURLElement.getText();
            var url = nameAndURLElement.getAttribute("href");
            var price = Double.parseDouble(priceElement.getText().replace(".", "").replace(",", "."));

            list.add(new Product(name, url, price));
        }

        return list;
    }
}
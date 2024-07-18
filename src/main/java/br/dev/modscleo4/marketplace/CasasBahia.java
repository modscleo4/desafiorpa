/**
 * Copyright 2024 Dhiego Cassiano Fogaça Barbosa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.dev.modscleo4.marketplace;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.dev.modscleo4.product.Product;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CasasBahia implements IMarketplace {
    @Override
    public Marketplace get() {
        return Marketplace.CASAS_BAHIA;
    }

    @Override
    public String getSearchURL(@Nonnull String query) {
        return "https://www.casasbahia.com.br/" + query.replace(" ", "-") + "/b";
    }

    @Override
    public @Nonnull List<Product> crawl(@Nonnull WebDriver driver) {
        var list = new ArrayList<Product>();

        // Wait for the search results to load
        log.info("Waiting for search results to load");
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(d -> {
            var section = d.findElement(By.cssSelector("div.Item-sc-815c7bf9-0.epcKyG"));

            return section.isDisplayed();
        });

        log.info("Search results loaded, parsing items");
        var itemsContainer = driver.findElement(By.cssSelector("div.Item-sc-815c7bf9-0.epcKyG"));
        var items = itemsContainer.findElements(By.cssSelector("div.styles__ProductCardWrapper-sc-162770ad-3"));

        log.info("Found " + items.size() + " items");
        for (var item : items) {
            try {
                if (!item.isDisplayed()) {
                    // Skip hidden items
                    continue;
                }

                var nameAndURLElement = item.findElement(By.cssSelector("h3.product-card__title a"));
                var priceElement = item.findElement(By.cssSelector("div.product-card__highlight-price"));

                var name = nameAndURLElement.getText();
                var url = nameAndURLElement.getAttribute("href");
                var price = Double.parseDouble(priceElement.getText().replaceFirst("^[^\\$]*\\$ ?", "").replace(".", "").replace(",", "."));

                list.add(new Product(get(), name, url, price));
            } catch (NoSuchElementException e) {
                log.error("Error parsing item", e);
            }
        }

        return list;
    }
}

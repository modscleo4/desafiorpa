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

package br.dev.modscleo4.crawler;

import java.util.List;
import javax.annotation.Nonnull;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import br.dev.modscleo4.marketplace.IMarketplace;
import br.dev.modscleo4.product.Product;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Crawler {
    private IMarketplace marketplace;
    private String query = "";
    private WebDriver driver;

    public Crawler() {
        var options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--headless=new");
        options.addArguments("--user-agent='Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36'");

        this.driver = new ChromeDriver(options);
    }

    public String getSearch() {
        return this.query;
    }

    public void setSearch(@Nonnull String query) {
        this.query = query;
    }

    public IMarketplace getMarketplace() {
        return this.marketplace;
    }

    public void setMarketplace(@Nonnull IMarketplace marketplace) {
        this.marketplace = marketplace;
    }

    public List<Product> start() {
        String url = marketplace.getSearchURL(this.query);

        log.info("Crawling started for " + this.marketplace.getClass().getSimpleName());
        log.info("Searching for " + query);
        log.info("URL: " + url);

        driver.get(url);
        var products = marketplace.crawl(driver);

        log.info("Crawling finished, found " + products.size() + " products");

        return products;
    }

    public void close() {
        driver.quit();
    }
}

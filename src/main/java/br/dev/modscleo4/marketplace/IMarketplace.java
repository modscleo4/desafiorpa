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

import java.util.List;

import javax.annotation.Nonnull;

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
    String getSearchURL(@Nonnull String query);

    /**
     * Crawl the website and get the products
     *
     * @param driver The WebDriver already initialized with the search URL
     * @return The products
     */
    List<Product> crawl(@Nonnull WebDriver driver);
}

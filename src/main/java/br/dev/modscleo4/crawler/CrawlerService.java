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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.dev.modscleo4.marketplace.IMarketplace;
import br.dev.modscleo4.product.Product;
import br.dev.modscleo4.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CrawlerService {
    @Autowired
    ProductRepository repository;

    public List<Product> crawl(IMarketplace[] marketplaces, String search) {
        var products     = new ArrayList<Product>();

        var crawler      = new Crawler();
        crawler.setSearch(search);
        log.info("Searching for " + search + " in " + marketplaces.length + " marketplaces");

        for (var marketplace : marketplaces) {
            crawler.setMarketplace(marketplace);
            products.addAll(crawler.start());
        }

        crawler.close();

        repository.saveAll(products);
        log.info("Saved " + products.size() + " products");

        var pageRequest = PageRequest.of(0, 3, Sort.by("price").ascending());
        for (var marketplace : marketplaces) {
            var marketplaceProducts = repository.findAllByMarketplace(marketplace.get(), pageRequest);
            log.info("Found " + marketplaceProducts.size() + " products from " + marketplace.get());
            for (var product : marketplaceProducts) {
                log.info(product.toString());
            }
        }

        return products;
    }
}

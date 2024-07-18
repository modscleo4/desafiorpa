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

package br.dev.modscleo4;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import br.dev.modscleo4.crawler.CrawlerService;
import br.dev.modscleo4.marketplace.CasasBahia;
import br.dev.modscleo4.marketplace.IMarketplace;
import br.dev.modscleo4.marketplace.MercadoLivre;

@SpringBootTest
@Rollback
public class CrawlerServiceTest {
    @Autowired
    private CrawlerService service;

    @Test
    public void contextLoads() {
        assertThat(service).isNotNull();
    }

    @Test
    public void testCrawlNoMarketplace() {
        var marketplaces = new IMarketplace[] {};
        var products = service.crawl(marketplaces, "PlayStation 5");

        assertThat(products).isNotNull();
        assertThat(products.size() == 0).isTrue();
    }

    @Test
    public void testCrawlMercadoLivre() {
        var marketplaces = new IMarketplace[] { new MercadoLivre() };
        var products = service.crawl(marketplaces, "PlayStation 5");

        assertThat(products).isNotNull();
        assertThat(products.size() > 0).isTrue();
    }

    @Test
    public void testCrawlCasasBahia() {
        var marketplaces = new IMarketplace[] { new CasasBahia() };
        var products = service.crawl(marketplaces, "PlayStation 5");

        assertThat(products).isNotNull();
        assertThat(products.size() > 0).isTrue();
    }
}

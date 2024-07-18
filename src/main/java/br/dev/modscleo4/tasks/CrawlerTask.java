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

package br.dev.modscleo4.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.dev.modscleo4.crawler.CrawlerService;
import br.dev.modscleo4.marketplace.CasasBahia;
import br.dev.modscleo4.marketplace.IMarketplace;
import br.dev.modscleo4.marketplace.MercadoLivre;
import br.dev.modscleo4.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Profile("!test")
public class CrawlerTask {
    @Autowired
    ProductRepository repository;

    @Autowired
    CrawlerService service;

    @Scheduled(initialDelay = 1000)
    public void crawlAll() throws Exception {
        repository.deleteAll();
        log.info("Products truncated");

        var marketplaces = new IMarketplace[] { new MercadoLivre(), new CasasBahia() };
        service.crawl(marketplaces, "Xbox Series S");
    }
}

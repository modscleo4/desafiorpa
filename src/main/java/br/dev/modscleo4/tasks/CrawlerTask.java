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

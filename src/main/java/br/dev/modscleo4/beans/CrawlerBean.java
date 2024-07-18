package br.dev.modscleo4.beans;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import br.dev.modscleo4.crawler.Crawler;
import br.dev.modscleo4.marketplace.CasasBahia;
import br.dev.modscleo4.marketplace.IMarketplace;
import br.dev.modscleo4.marketplace.MercadoLivre;
import br.dev.modscleo4.product.Product;
import br.dev.modscleo4.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CrawlerBean implements CommandLineRunner {
    @Autowired
    ProductRepository repository;

    @Override
    public void run(String... args) throws Exception {
        repository.deleteAll();
        log.info("products truncated");

        var products     = new ArrayList<Product>();
        var marketplaces = new IMarketplace[] { new MercadoLivre(), new CasasBahia() };
        var crawler      = new Crawler();
        crawler.setSearch("Xbox Series S");

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
    }
}

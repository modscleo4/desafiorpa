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

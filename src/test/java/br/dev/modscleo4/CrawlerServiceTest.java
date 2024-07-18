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

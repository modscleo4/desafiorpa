package br.dev.modscleo4.product;

import java.io.Serializable;

import br.dev.modscleo4.marketplace.IMarketplace;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "products")
@Getter @Setter @NoArgsConstructor
public class Product implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private IMarketplace.Marketplace marketplace;
    private String name;
    private String url;
    private double price;

    public Product(IMarketplace.Marketplace marketplace, String name, String url, double price) {
        this.marketplace = marketplace;
        this.name = name;
        this.url = url;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Product[id=%d, marketplace=%s, name=%s, url=%s, price=%.2f]", id, marketplace, name, url, price);
    }
}

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

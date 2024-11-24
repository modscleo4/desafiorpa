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
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.dev.modscleo4.marketplace.IMarketplace;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "products")
@Getter @Setter @NoArgsConstructor @ToString
public class Product implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private IMarketplace.Marketplace marketplace;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String url;
    @DecimalMin("0.00") @Column(nullable = false)
    private double price;

    @CreationTimestamp @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @UpdateTimestamp @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Product(IMarketplace.Marketplace marketplace, String name, String url, double price) {
        this.marketplace = marketplace;
        this.name = name;
        this.url = url;
        this.price = price;
    }
}

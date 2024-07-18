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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import br.dev.modscleo4.marketplace.IMarketplace.Marketplace;
import br.dev.modscleo4.product.Product;
import br.dev.modscleo4.product.ProductRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Rollback
@FlywayTest(invokeCleanDB = true)
public class ProductControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository repository;

    @Test
    public void contextLoads() throws Exception {
        assertThat(mvc).isNotNull();
        assertThat(repository).isNotNull();
    }

    @Test
    public void testGetAll() throws Exception {
        var product = new Product(Marketplace.MERCADO_LIVRE, "test", "www.example.com", 4.44);
        assertThat(repository.save(product)).isNotNull();

        try {
            var response = mvc.perform(get("/products"));
            response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].marketplace").value(product.getMarketplace().toString()))
                .andExpect(jsonPath("$[0].name").value(product.getName()))
                .andExpect(jsonPath("$[0].url").value(product.getUrl()))
                .andExpect(jsonPath("$[0].price").value(product.getPrice()));
        } finally {
            repository.delete(product);
        }
    }

    @Test
    public void testGetById() throws Exception {
        var product = new Product(Marketplace.MERCADO_LIVRE, "test", "www.example.com", 4.44);
        assertThat(repository.save(product)).isNotNull();

        try {
            var response = mvc.perform(get("/products/{id}", product.getId()));
            response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.marketplace").value(product.getMarketplace().toString()))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.url").value(product.getUrl()))
                .andExpect(jsonPath("$.price").value(product.getPrice()));
        } finally {
            repository.delete(product);
        }
    }

    @Test
    public void testGetByIdNotFound() throws Exception {
        var response = mvc.perform(get("/products/{id}", 9999));
        response.andExpect(status().isNotFound());
    }
}

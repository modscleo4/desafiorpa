package br.dev.modscleo4.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository repository;

    @GetMapping("")
    public List<Product> all() {
        return repository.findAll(Sort.by("price"));
    }

    @GetMapping("/{id}")
    public Product one(@PathVariable(required = true, name = "id") Long id) {
        return repository.findById(id).orElse(null);
    }
}

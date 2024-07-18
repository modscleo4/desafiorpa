package br.dev.modscleo4.product;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.dev.modscleo4.marketplace.IMarketplace;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>, ListPagingAndSortingRepository<Product, Long> {
    List<Product> findAllByMarketplace(IMarketplace.Marketplace marketplace, Pageable pageable);

    List<Product> findAllByName(String name, Pageable pageable);
}

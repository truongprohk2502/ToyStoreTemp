package code.repository;

import code.model.Toy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ToyRepository extends PagingAndSortingRepository<Toy, Long> {

    Page<Toy> findAllByBrand_NameContaining(String name, Pageable pageable);

    Page<Toy> findAllByCategory_IdIn(List<Long> names, Pageable pageable);

    Page<Toy> findAllByNameContaining(String name, Pageable pageable);

    Page<Toy> findAllByNameContainingAndOnSaleEquals(String name, Boolean onSale, Pageable pageable);

    Page<Toy> findAllByNameContainingOrderByManufacturingDateDesc(String name, Pageable pageable);

    Page<Toy> findAllByNameContainingOrderByPriceAsc(String name, Pageable pageable);

    Page<Toy> findAllByNameContainingOrderByPriceDesc(String name, Pageable pageable);

    Page<Toy> findAllByNameContainingAndPriceGreaterThanEqualAndPriceLessThanEqual(String word, Long price1, Long price2, Pageable pageable);

    Page<Toy> findAllByNameContainingAndPriceGreaterThanEqual(String word, Long price1, Pageable pageable);

    Page<Toy> findAllByNameContainingAndPriceLessThanEqual(String word, Long price2, Pageable pageable);
}

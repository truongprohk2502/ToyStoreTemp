package code.service;

import code.model.Toy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ToyService {

    List<Toy> findRandomToys();

    List<Toy> findRelatedToys();

    List<Toy> findBySellerId(Long id);

    Page<Toy> findBrandToys(Long id, Pageable pageable);

    Page<Toy> findCategoryToys(List<Long> idArr, Pageable pageable);

    Page<Toy> findAllByName(String name, String sorted, Pageable pageable);

    Page<Toy> findAllByPrice(String word, String price1, String price2, Pageable pageable);

    Toy findById(Long id);

    void save(Toy toy);

    void remove(Long id);

    void updateQuantityInStock(Long id, Long qty);

}


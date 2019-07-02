package code.service;

import code.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    Iterable<Category> findAll();

    List<Category> findAllByParentIdContaining(String index);

    String findCategoryName(Long id);

    List<Category> findRandomCategories(Long number);
}

package code.repository;

import code.model.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    List<Category> findAllByParentIdContaining(String parentId);
}

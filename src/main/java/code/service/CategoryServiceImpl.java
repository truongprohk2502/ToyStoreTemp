package code.service;

import code.model.Category;
import code.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findAllByParentIdContaining(String index) {
        return categoryRepository.findAllByParentIdContaining(index);
    }

    @Override
    public String findCategoryName(Long id) {
        TypedQuery<Category> query = em.createQuery("select c from Category c where c.id = :id", Category.class);
        query.setParameter("id", id);
        return query.getSingleResult().getName();
    }

    @Override
    public List<Category> findRandomCategories(Long number) {
        String query = "select * from Category order by rand() limit " + number;
        return em.createNativeQuery(query, Category.class).getResultList();
    }
}

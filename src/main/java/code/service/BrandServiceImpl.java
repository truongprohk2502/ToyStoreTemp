package code.service;

import code.model.Brand;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class BrandServiceImpl implements BrandService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Brand> findRandomBrands(Long number) {
        String query = "select * from Brand order by rand() limit " + number;
        return em.createNativeQuery(query, Brand.class).getResultList();
    }

    @Override
    public String findBrandName(Long id) {
        TypedQuery<Brand> query = em.createQuery("select b from Brand b where b.id = :id", Brand.class);
        query.setParameter("id", id);
        return query.getSingleResult().getName();
    }
}

package code.service;

import code.model.Brand;
import code.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Brand> findRandomBrands(Long number) {
        String query = "select * from Brand order by rand() limit " + number;
        return em.createNativeQuery(query, Brand.class).getResultList();
    }

    @Override
    public String findBrandName(Long id) {
        return brandRepository.findOne(id).getName();
    }

}

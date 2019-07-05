package code.service;

import code.model.Brand;
import code.model.Toy;
import code.repository.ToyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class ToyServiceImpl implements ToyService {

    @Autowired
    private ToyRepository toyRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Toy> findRandomToys() {
        return em.createNativeQuery("select * from Toy order by rand() limit 8", Toy.class).getResultList();
    }

    @Override
    public List<Toy> findRelatedToys() {
        return em.createNativeQuery("select * from Toy order by rand() limit 6", Toy.class).getResultList();
    }

    @Override
    public Page<Toy> findBrandToys(Long id, Pageable pageable) {
        TypedQuery<Brand> query = em.createQuery("select b from Brand b where b.id = :id", Brand.class);
        query.setParameter("id", id);
        return toyRepository.findAllByBrand_NameContaining(query.getSingleResult().getName(), pageable);
    }

    @Override
    public Page<Toy> findCategoryToys(List<Long> idArr, Pageable pageable) {
        return toyRepository.findAllByCategory_IdIn(idArr, pageable);
    }

    @Override
    public Page<Toy> findAllByName(String name, String sorted, Pageable pageable) {

        Page<Toy> toys = null;

        switch (sorted) {
            case "none":
                toys = toyRepository.findAllByNameContaining(name, pageable);
                break;
            case "sale":
                toys = toyRepository.findAllByNameContainingAndOnSaleEquals(name, true, pageable);
                break;
            case "new":
                toys = toyRepository.findAllByNameContainingOrderByManufacturingDateDesc(name, pageable);
                break;
            case "high":
                toys = toyRepository.findAllByNameContainingOrderByPriceDesc(name, pageable);
                break;
            case "low":
                toys = toyRepository.findAllByNameContainingOrderByPriceAsc(name, pageable);
                break;
        }

        return toys;
    }

    @Override
    public Page<Toy> findAllByPrice(String word, String price1, String price2, Pageable pageable) {

        if (!"".equals(price1) && !"".equals(price2)) {
            return toyRepository.findAllByNameContainingAndPriceGreaterThanEqualAndPriceLessThanEqual(word, Long.parseLong(price1), Long.parseLong(price2), pageable);
        }

        if (!"".equals(price1) && "".equals(price2)) {
            return toyRepository.findAllByNameContainingAndPriceGreaterThanEqual(word, Long.parseLong(price1), pageable);
        }

        if ("".equals(price1) && !"".equals(price2)) {
            return toyRepository.findAllByNameContainingAndPriceLessThanEqual(word, Long.parseLong(price2), pageable);
        }

        return null;
    }

    @Override
    public Toy findById(Long id) {
        return toyRepository.findOne(id);
    }

    @Override
    public void save(Toy toy) {
        toyRepository.save(toy);
    }

    @Override
    public void updateQuantityInStock(Long id, Long qty) {
        toyRepository.updateQuantityInStock(id, qty);
    }

}

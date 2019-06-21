package code.service;

import code.model.Brand;

import java.util.List;

public interface BrandService {

    List<Brand> findRandomBrands(Long number);

    String findBrandName(Long id);
}

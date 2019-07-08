package code.service;


import code.model.Seller;

public interface SellerService {

    Iterable<Seller> findAll();
    Seller findSellerByUsername(String username);
    Seller findSellerByEmail(String email);
    Seller findSellerByCertificateCode(Long certificate);
    void save(Seller seller);
}

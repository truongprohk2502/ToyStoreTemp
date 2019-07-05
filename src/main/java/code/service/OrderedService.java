package code.service;

import code.model.Ordered;

import java.util.List;

public interface OrderedService {

    void save(Ordered ordered);

    List<Ordered> findBestSeller();

    List<Ordered> findAllByAccount_Username(String username);

}

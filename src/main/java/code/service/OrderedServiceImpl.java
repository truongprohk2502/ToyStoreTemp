package code.service;

import code.model.Ordered;
import code.repository.OrderedRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderedServiceImpl implements OrderedService {

    @Autowired
    private OrderedRepository orderedRepository;

    @Override
    public void save(Ordered ordered) {
        orderedRepository.save(ordered);
    }
}

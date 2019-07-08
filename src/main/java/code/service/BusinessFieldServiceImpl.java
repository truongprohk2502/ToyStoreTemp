package code.service;

import code.model.BusinessField;
import code.repository.BusinessFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BusinessFieldServiceImpl implements BusinessFieldService {
    @Autowired
    private BusinessFieldRepository businessFieldRepository;
    @Override
    public List<BusinessField> findAll() {
        return businessFieldRepository.findAll();
    }
}

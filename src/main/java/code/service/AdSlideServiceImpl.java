package code.service;

import code.model.AdSlide;
import code.repository.AdSlideRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AdSlideServiceImpl implements AdSlideService {

    @Autowired
    private AdSlideRepository adSlideRepository;

    @Override
    public Iterable<AdSlide> findAll() {
        return adSlideRepository.findAll();
    }

}

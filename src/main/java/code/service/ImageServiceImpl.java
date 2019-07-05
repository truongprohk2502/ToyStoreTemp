package code.service;

import code.model.Image;
import code.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<Image> findAllByToyId(Long id) {
        return imageRepository.findAllByToy_Id(id);
    }

}

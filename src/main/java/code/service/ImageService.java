package code.service;

import code.model.Image;

import java.util.List;

public interface ImageService {

    List<Image> findAllByToyId(Long id);

}

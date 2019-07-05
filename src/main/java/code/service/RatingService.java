package code.service;

import code.model.Rating;

import java.util.List;

public interface RatingService {

    List<Rating> findAllByToyId(Long id);

    List<Rating> findAllByParentIdAndToy_Id(Long parentId, Long toyId);

    List<Rating> findAllByAccount_UsernameAndToy_Id(String username, Long toyId);

    void save(Rating rating);

    void updateRatingStar(Long toyId, Long accountId, Long ratingStar);
}

package code.service;

import code.model.District;

import java.util.List;

public interface DistrictService {

    List<District> findAllByParentId(Long parentId);

}

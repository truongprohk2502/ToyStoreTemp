package code.service;

import code.model.Village;

import java.util.List;

public interface VillageService {

    List<Village> findAllByParentId(Long parentId);

}

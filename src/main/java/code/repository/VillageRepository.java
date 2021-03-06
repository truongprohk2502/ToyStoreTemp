package code.repository;

import code.model.Village;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VillageRepository extends JpaRepository<Village, Long> {

    List<Village> findAllByParentId(Long parentId);

}

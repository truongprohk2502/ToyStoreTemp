package code.repository;

import code.model.Ordered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderedRepository extends JpaRepository<Ordered, Long> {

    List<Ordered> findAllByAccount_Username(String username);

}

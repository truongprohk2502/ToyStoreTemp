package code.repository;

import code.model.Ordered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface OrderedRepository extends JpaRepository<Ordered, Long> {

}

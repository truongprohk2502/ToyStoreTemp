package code.repository;

import code.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User,Long> {
    User findByUsername(String username);
}

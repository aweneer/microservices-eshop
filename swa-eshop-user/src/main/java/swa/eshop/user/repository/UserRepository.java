package swa.eshop.user.repository;

import org.springframework.data.repository.CrudRepository;
import swa.eshop.user.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}

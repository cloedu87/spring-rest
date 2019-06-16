package ch.berawan.springrest.security.data.repository;

import ch.berawan.springrest.security.data.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(final String username);

}

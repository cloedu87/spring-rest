package ch.berawan.springrest.data.repository;

import ch.berawan.springrest.data.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(final String username);

}

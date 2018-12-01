package ca.uvic.seng330.assn3.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import ca.uvic.seng330.assn3.models.User;

/**
 * Resources on JPA / Repository
 * https://spring.io/guides/gs/accessing-data-jpa/
 *
 * When creating a new user, or checking the h2 database, you can check here:
 *      http://localhost:8080/h2-console
 *
 * In the JDCB url input field, put this:
 *      jdbc:h2:file:./src/main/resources/DB
 *
 * Hit 'connect' and you'll be able to see the list of users.
 *
 */
public interface UserRepository extends CrudRepository<User, UUID> {

    List<User> findByUsername(String username);

    List<User> findByIsAdmin(boolean isAdmin);

}
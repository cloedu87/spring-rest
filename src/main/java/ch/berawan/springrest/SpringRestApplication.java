package ch.berawan.springrest;

import ch.berawan.springrest.data.entity.User;
import ch.berawan.springrest.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class SpringRestApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SpringRestApplication.class, args);
    }

    public void run(String... args) throws Exception {
        if (this.userRepository.findByUsername("awan") == null) {
            final User user = new User("awan awan", "awan", passwordEncoder.encode("berawan"), Arrays.asList("ADMIN"));

            this.userRepository.save(user);
        }
    }
}
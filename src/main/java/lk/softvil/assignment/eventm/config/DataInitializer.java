package lk.softvil.assignment.eventm.config;

import lk.softvil.assignment.eventm.model.entity.User;
import lk.softvil.assignment.eventm.model.enums.UserRole;
import lk.softvil.assignment.eventm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.findByEmail("admin@eventm.com").isEmpty()) {
            User admin = User.builder()
                    .email("admin@eventm.com")
                    .name("Admin")
                    .role(UserRole.ADMIN)
                    .status(true).build();

            userRepository.save(admin);
            System.out.println("Admin user created.");
        } else {
            System.out.println("Admin user already exists.");
        }
    }
}

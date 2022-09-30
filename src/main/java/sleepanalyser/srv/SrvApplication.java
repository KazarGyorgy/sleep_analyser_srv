package sleepanalyser.srv;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sleepanalyser.srv.Entities.Role;
import sleepanalyser.srv.Entities.User;
import sleepanalyser.srv.services.RoleService;
import sleepanalyser.srv.services.UserService;

import java.util.ArrayList;

@SpringBootApplication
public class SrvApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrvApplication.class, args);
    }

    @Bean CommandLineRunner run(UserService userService, RoleService roleService) {
        return args -> {
            roleService.saveRole(new Role(null, "USER", "The User role"));
            roleService.saveRole(new Role(null, "ADMIN", "Admin role"));
            roleService.saveRole(new Role(null, "DOCTOR", "Doctor role"));
            userService.saveUser(new User(null, "György", "Kazár", "kgyuri",
                    "1234", new ArrayList<>()));
            userService.saveUser(new User(null, "Teszt", "Elek", "telek",
                    "1234", new ArrayList<>()));
            userService.saveUser(new User(null, "Gipsz", "Elek", "gipszelek",
                    "1234", new ArrayList<>()));
            userService.addUserToRole("kgyuri", "ADMIN");
            userService.addUserToRole("telek", "USER");
            userService.addUserToRole("gipszelek", "DOCTOR");
        };
    }
}

package sleepanalyser.srv;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import sleepanalyser.srv.Entities.Role;
import sleepanalyser.srv.Entities.User;
import sleepanalyser.srv.services.RoleService;
import sleepanalyser.srv.services.UserService;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
public class SrvApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrvApplication.class, args);

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("GET", "POST", "PATCH","DELETE");
            }
        };
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

//    @Bean
//    CommandLineRunner run(UserService userService, RoleService roleService) {
//        return args -> {
//            roleService.saveRole(new Role(null, "PATIENT", "The patient role"));
//            roleService.saveRole(new Role(null, "ADMIN", "Admin role"));
//            roleService.saveRole(new Role(null, "DOCTOR", "Doctor role"));
//            userService.saveUser(new User(null, "György", "Kazár", "kgyuri",
//                    "1234",  5630, "", "", 107862875L,
//                    "36304181341", "kazargyuri93@gmail.com", new Date(1993,11,10), new ArrayList<>()));
//
//
//            userService.addUserToRole("kgyuri", "ADMIN");
//
//
//        };
//    }
}

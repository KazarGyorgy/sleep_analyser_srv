package sleepanalyser.srv.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sleepanalyser.srv.Entities.Role;
import sleepanalyser.srv.Entities.User;
import sleepanalyser.srv.Repositories.RoleRepository;
import sleepanalyser.srv.Repositories.UserRepository;
import sleepanalyser.srv.services.UserService;

import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImp implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user, String rolename) {
        log.info("User " + user.getUsername() + "is being saved");
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String generatedPassword = RandomStringUtils.random( 15, characters );
        System.out.println(generatedPassword);
        if (user.getUsername() == null || user.getPassword().isEmpty() && user.getUsername().isEmpty()) {
            user.setPassword(generatedPassword);

            user.setUsername(user.getLastName().toLowerCase().substring(0, 2) + user.getFirstName());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        this.addUserToRole(savedUser.getUsername(), "DOCTOR");

        return  savedUser;
    }

    @Override

    public void addUserToRole(String username, String rolename) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(rolename);
        log.info("User " + user.getUsername() + " and the role " + role.getName() + " are under joining");
        user.getRoles().add(role);
    }

    @Override
    public User getUserByUsername(String username) {
        log.info("trying to get {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsersByRoleName(String rolename) {
        return userRepository.findByRolesName(rolename);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Nem található a felhasználó :" + username);
        } else {
            log.info("User found {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}

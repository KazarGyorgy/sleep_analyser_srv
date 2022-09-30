package sleepanalyser.srv.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sleepanalyser.srv.Entities.Role;
import sleepanalyser.srv.Entities.User;
import sleepanalyser.srv.Repositories.RoleRepository;
import sleepanalyser.srv.Repositories.UserRepository;
import sleepanalyser.srv.services.UserService;

import javax.transaction.Transactional;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    public User saveUser(User user) {
        log.info("User " + user.getUsername() + "is being saved");
        return userRepository.save(user);
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
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}

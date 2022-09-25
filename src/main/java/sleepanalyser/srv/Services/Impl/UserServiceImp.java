package sleepanalyser.srv.Services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sleepanalyser.srv.Entities.Role;
import sleepanalyser.srv.Entities.User;
import sleepanalyser.srv.Repositories.RoleRepository;
import sleepanalyser.srv.Repositories.UserRepository;
import sleepanalyser.srv.Services.UserService;

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
        log.info("User " + user.getTajNumber() + "is being saved");
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Role " + role.getRoleName() + "is being saved");
        return roleRepository.save(role);
    }

    @Override
    public User getUserByTaj(Long taj) {
        return userRepository.findByTajNumber(taj);
    }

}

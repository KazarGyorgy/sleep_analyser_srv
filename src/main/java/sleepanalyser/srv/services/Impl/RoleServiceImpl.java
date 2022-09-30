package sleepanalyser.srv.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sleepanalyser.srv.Entities.Role;
import sleepanalyser.srv.Repositories.RoleRepository;
import sleepanalyser.srv.services.RoleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;


    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}

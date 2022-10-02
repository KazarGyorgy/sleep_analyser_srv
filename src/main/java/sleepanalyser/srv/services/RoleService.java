package sleepanalyser.srv.services;

import sleepanalyser.srv.Entities.Role;

import java.util.List;

public interface RoleService {

    /**
     * This function save new role.
     * @param role
     * @return the saved role entity.
     */
    Role saveRole(Role role);

    Role getRoleByName(String roleName);

    List<Role> getRoles();
}

package sleepanalyser.srv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import sleepanalyser.srv.Entities.Role;
import sleepanalyser.srv.Repositories.RoleRepository;
import sleepanalyser.srv.services.RoleService;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleTests {
    @Autowired
    private RoleService roleService;
    @MockBean
    private RoleRepository roleRepository;

    @Test
    public void getAllRolesTest() {
        when(roleRepository.findAll()).thenReturn(Stream.of(
                new Role(1l, "PATIENT", "Patient role"),
                new Role(2l, "ADMIN", "Admin role"),
                new Role(3l, "DOCTOR", "Admin role")
        ).collect(Collectors.toList()));
        assertEquals(3, roleService.getRoles().size());
    }

    @Test
    public void getRoleByNameTest() {
        String name = "ADMIN";
        Role admin = new Role(2l, "ADMIN", "Admin role");
        when(roleRepository.findByName(name)).thenReturn(admin);
        assertEquals(admin, roleService.getRoleByName(name));
    }

    @Test
    public void saveRoleTest() {
        Role role = new Role(4L, "TEST", "Test role");
        when(roleRepository.save(role)).thenReturn(role);
        assertEquals(role, roleService.saveRole(role));
    }
}

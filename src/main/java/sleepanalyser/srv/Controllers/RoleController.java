package sleepanalyser.srv.Controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sleepanalyser.srv.Entities.Role;
import sleepanalyser.srv.services.RoleService;
import sleepanalyser.srv.services.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@Slf4j
public class RoleController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok().body(this.roleService.getRoles());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Role> getRoleByName(@RequestParam String name) {
        return ResponseEntity.ok().body(roleService.getRoleByName(name));
    }

    @PostMapping()
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/role").toUriString());
        return ResponseEntity.created(uri).body(this.roleService.saveRole(role));
    }

    @PostMapping("/add-to-user/{username}/{rolename}")
    public ResponseEntity<?> addToUser(@RequestParam String username, @RequestParam String rolename) {
        this.userService.addUserToRole(username, rolename);
        return ResponseEntity.ok().build();
    }
}

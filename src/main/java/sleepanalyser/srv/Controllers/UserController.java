package sleepanalyser.srv.Controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sleepanalyser.srv.Entities.User;
import sleepanalyser.srv.services.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("{roleType}")
    public ResponseEntity<User> saveUser(@RequestBody User user, @PathVariable String roleType) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user").toUriString());

        return  ResponseEntity.created(uri).body(userService.saveUser(user, roleType.toUpperCase()));
    }

    @GetMapping("/users/{roleType}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String roleType) {
    log.info(roleType);

    return ResponseEntity.ok().body(this.userService.getUsersByRoleName(roleType.toUpperCase()));
    }
}

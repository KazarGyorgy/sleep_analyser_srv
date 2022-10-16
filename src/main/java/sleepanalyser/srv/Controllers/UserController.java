package sleepanalyser.srv.Controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sleepanalyser.srv.Dto.UserDetailsDTO;
import sleepanalyser.srv.Entities.User;
import sleepanalyser.srv.services.UserService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("{roleType}")
    public ResponseEntity<User> saveUser(@RequestBody User user, @PathVariable String roleType) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user").toUriString());

        return  ResponseEntity.created(uri).body(userService.saveUser(user, roleType.toUpperCase()));
    }

    @GetMapping("/users/{roleType}")
    public ResponseEntity<List<UserDetailsDTO>> getUsersByRole(@PathVariable String roleType) {
    log.info(roleType);

    List<User> users = this.userService.getUsersByRoleName(roleType.toUpperCase());


    return ResponseEntity.ok().body(users.stream().map(this::convertUserToDto).collect(Collectors.toList()));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        log.info("delete started");
       boolean isRemoved = userService.deleteUser(userId);

        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return (ResponseEntity<?>) ResponseEntity.ok();
    }

    @PatchMapping("/{userId}")
    public void updateUser(@PathVariable String userId, @RequestBody User user) throws ChangeSetPersister.NotFoundException {
        this.userService.updateUser(userId,user);
    }
    private UserDetailsDTO convertUserToDto(User user) {
        UserDetailsDTO dto = modelMapper.map(user, UserDetailsDTO.class);
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUsername(user.getUsername());
        dto.setCity(user.getCity());
        dto.setZip(user.getZip());
        dto.setStreetAddress(user.getStreetAddress());
        dto.setBirthdate(dto.getBirthdate());
        dto.setTajNumber(user.getTajNumber());
        dto.setDrId(user.getDrId());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setEmail(user.getEmail());

        return dto;
    }
}

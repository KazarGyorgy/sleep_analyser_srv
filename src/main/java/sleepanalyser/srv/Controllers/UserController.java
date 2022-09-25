package sleepanalyser.srv.Controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;;
import org.springframework.web.bind.annotation.*;
import sleepanalyser.srv.Entities.User;
import sleepanalyser.srv.Services.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private UserService userService;

    @GetMapping("/")
    private User signin(@RequestParam Long tajNumber) {
        return userService.getUserByTaj(tajNumber);
    }
    @PostMapping("/")
    private void saveUser(User user) {
            userService.saveUser(user);
    }

}

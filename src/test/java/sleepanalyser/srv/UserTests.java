package sleepanalyser.srv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import sleepanalyser.srv.Entities.Role;
import sleepanalyser.srv.Entities.User;
import sleepanalyser.srv.Repositories.UserRepository;
import sleepanalyser.srv.services.UserService;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

    @Autowired
    private UserService service;

    @MockBean
    private UserRepository repository;

    @Test
    public void saveUserTest() {
        Role adminRole = new Role(2L, "ADMIN", "Admin role");
        User user = new User(4L, "György", "Kazár", "kgyuri", "1234", 5630,
                "Békés", "Szabó Dezső utca 13", "107865862", "",
                "+36304181341", "asd@asd.hu", new Date(), new ArrayList<>(), new User());
        when(repository.save(user)).thenReturn(user);
        assertEquals(user, service.saveUser(user, "Admin"));
    }

}
//    User saveUser(User user, String rolename);
//
//    /**
//     * this function mapping role and user .
//     * @param username
//     * @param rolename
//     */
//    void addUserToRole(String username, String rolename);
//
//
//    /**
//     * Get user entity by username
//     * @param username
//     * @return user
//     */
//    User getUserByUsername(String username);
//
//    /**
//     * Get users by role name
//     * @param roleName name of the role.
//     * @return List of users
//     */
//    List<User> getUsersByRoleName(String roleName);
//
//    /**
//     * Delete user
//     * @param userId Long user id.
//     * @return boolean
//     */
//    boolean deleteUser(Long userId);
//
//    /**
//     * Update user entity.
//     * @param userId userId;
//     * @param user The modified data-
//     * @return a saved user entity.
//     * @throws ChangeSetPersister.NotFoundException
//     */
//    User updateUser(String userId, User user) throws ChangeSetPersister.NotFoundException;
//
//    /**
//     * Change Password.
//     * @param oldPassword .
//     * @param newPassword .
//     * @return boolean after save.
//     */
//    boolean changePassword( String oldPassword, String newPassword);
//
//    /**
//     * Find user by id;
//     * @param userId userid.
//     * @return Opitonal user.
//     */
//    Optional<User> findById(Long userId);
//}
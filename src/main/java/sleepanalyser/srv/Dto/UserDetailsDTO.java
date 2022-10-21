package sleepanalyser.srv.Dto;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
@Data
public class UserDetailsDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private int zip;
    private String City;
    private String streetAddress;
    private String tajNumber;
    private String drId;
    private String phoneNumber;
    private String email;
    private Date birthdate;

}

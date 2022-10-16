package sleepanalyser.srv.Dto;

import lombok.Data;

@Data
public class ChangePasswordDto {
    public String oldPassword;
    public String newPassword;
}

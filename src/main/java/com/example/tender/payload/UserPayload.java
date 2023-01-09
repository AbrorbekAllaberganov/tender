package uz.abror.myproject.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserPayload {
    private String fullName;
    private String userName;
    private String password;
    private String phoneNumber;
}

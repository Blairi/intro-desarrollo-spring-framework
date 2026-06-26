package mx.com.unam.sispro3d.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Data
@Component
public class Account {
    @Value("${ACCOUNT_ID}")
    private int idUser;
    @Value("${ACCOUNT_NAME}")
    private String name;
    @Value("${ACCOUNT_LAST_NAME}")
    private String lastName;
    @Value("${ACCOUNT_EMAIL}")
    private String email;
    @Value("${ACCOUNT_PHONE}")
    private String phone;
    @Value("${ACCOUNT_USER_TYPE}")
    private UserType type;
    private LocalDateTime createdAt = LocalDateTime.now();
}
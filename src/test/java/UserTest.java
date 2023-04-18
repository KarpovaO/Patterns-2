import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserTest {

    private String login;
    private String password;
    private String status;

    public UserTest(String login, String password, String status) {
        this.login = login;
        this.password = password;
        this.status = status;
    }
}

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

@UtilityClass
public class DataGeneratorTest {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void sendRequest(UserTest userInfo) {
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(userInfo) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @UtilityClass
    public static class Registration {
        static final Faker faker = new Faker();

        public static String GetRandomLogin() {
            String login = faker.name().username();
            return login;
        }

        public static String GetRandomPassword() {
            String password = faker.internet().password();
            return password;
        }

        public static UserTest generateUser(String status) {
            return new UserTest(
                    GetRandomLogin(),
                    GetRandomPassword(),
                    status //faker.bool().bool() ? "active" : "blocked"
            );
        }
    }
}

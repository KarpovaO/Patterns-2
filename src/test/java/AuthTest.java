import com.codeborne.selenide.Condition;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AuthTest {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static String login;
    private static String password;
    private static String status;

    @BeforeAll
    static void setUpAll() {
        User info = DataGenerator.Registration.generateUser();
        login = info.getLogin();
        password = info.getPassword();
        status = info.getStatus();

        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(info) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @Test
    void shouldCreateUserAndLogin() {
        open("http://localhost:9999");

        $("[data-test-id= 'login'] input").setValue(login);
        $("[data-test-id= 'password'] input").setValue(password);
        $("[data-test-id= 'action-login']").click();
        if (Objects.equals(status, "active")) {
            $(".App_appContainer__3jRx1").shouldHave(text("Личный кабинет"));
        } else {
            $(".notification__content").shouldHave(Condition.text("Пользователь заблокирован"));
        }

    }

    @Test
    public void loginWithInvalidLogin() {
        LoginPage loginPage = open("http://localhost:9999", LoginPage.class);
        User user = DataGenerator.Registration.generateUser();
        loginPage.login("invalidLogin", user.getPassword());
        assertTrue(loginPage.isErrorMessageDisplayed());
    }

    @Test
    public void loginWithInvalidPassword() {
        LoginPage loginPage = open("http://localhost:9999", LoginPage.class);
        User user = DataGenerator.Registration.generateUser();
        loginPage.login(user.getLogin(), "invalidPassword");
        assertTrue(loginPage.isErrorMessageDisplayed());
    }

}

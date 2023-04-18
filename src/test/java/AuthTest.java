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

    @Test
    void shouldLoginValidUser() {
        UserTest userInfo = DataGeneratorTest.Registration.generateUser("active");
        DataGeneratorTest.sendRequest(userInfo);

        open("http://localhost:9999");

        $("[data-test-id= 'login'] input").setValue(userInfo.getLogin());
        $("[data-test-id= 'password'] input").setValue(userInfo.getPassword());
        $("[data-test-id= 'action-login']").click();
        $(".App_appContainer__3jRx1").shouldHave(text("Личный кабинет"));
    }

    @Test
    void shouldNotLoginBlockedUser() {
        UserTest userInfo = DataGeneratorTest.Registration.generateUser("blocked");
        DataGeneratorTest.sendRequest(userInfo);

        open("http://localhost:9999");

        $("[data-test-id= 'login'] input").setValue(userInfo.getLogin());
        $("[data-test-id= 'password'] input").setValue(userInfo.getPassword());
        $("[data-test-id= 'action-login']").click();
        $(".notification__content").shouldHave(Condition.text("Пользователь заблокирован"));
    }

    @Test
    void shouldNotLoginNotRegisteredUser() {
        open("http://localhost:9999");

        $("[data-test-id= 'login'] input").setValue(DataGeneratorTest.Registration.GetRandomLogin());
        $("[data-test-id= 'password'] input").setValue(DataGeneratorTest.Registration.GetRandomPassword());
        $("[data-test-id= 'action-login']").click();
        $(".notification__content").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotLoginInvalidUserName() {
        UserTest userInfo = DataGeneratorTest.Registration.generateUser("active");
        DataGeneratorTest.sendRequest(userInfo);

        open("http://localhost:9999");

        $("[data-test-id= 'login'] input").setValue(DataGeneratorTest.Registration.GetRandomLogin());
        $("[data-test-id= 'password'] input").setValue(userInfo.getPassword());
        $("[data-test-id= 'action-login']").click();
        $(".notification__content").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));

    }

    @Test
    void shouldNotLoginInvalidUserPassword() {
        UserTest userInfo = DataGeneratorTest.Registration.generateUser("active");
        DataGeneratorTest.sendRequest(userInfo);

        open("http://localhost:9999");

        $("[data-test-id= 'login'] input").setValue(userInfo.getLogin());
        $("[data-test-id= 'password'] input").setValue(DataGeneratorTest.Registration.GetRandomLogin());
        $("[data-test-id= 'action-login']").click();
        $(".notification__content").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

}

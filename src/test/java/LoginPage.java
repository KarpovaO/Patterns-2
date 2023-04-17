import static com.codeborne.selenide.Selenide.*;

public class LoginPage {

    public void login(String login, String password) {
        String LOGIN_INPUT = "[data-test-id=login] input";
        $(LOGIN_INPUT).setValue(login);

        String PASSWORD_INPUT = "[data-test-id=password] input";
        $(PASSWORD_INPUT).setValue(password);

        String SUBMIT_BUTTON = "[data-test-id=action-login]";
        $(SUBMIT_BUTTON).click();
    }

    public boolean isErrorMessageDisplayed() {
        String ERROR_MESSAGE = "[data-test-id=error-notification]";
        return $(ERROR_MESSAGE).isDisplayed();
    }
}
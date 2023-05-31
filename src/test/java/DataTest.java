import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DataTest {
    @Test
    void validActive() {
        Info user = DataGenirator.genirateValidActiv();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("[data-test-id=action-login]").click();
        $("h2.heading_theme_alfa-on-white").shouldHave(text("Личный кабинет"));
    }

    @Test
    void validBlocked() {
        Info user = DataGenirator.genirateValidBlocked();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка"));
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void invalidLogin() {
        Info user = DataGenirator.genirateInvalidLogin();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка"));
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void invalidPassword() {
        Info user = DataGenirator.generateInvalidPassword();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка"));
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void noPassword() {
        Info user = DataGenirator.genirateValidActiv();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=password]").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void noLogin() {
        Info user = DataGenirator.genirateValidActiv();
        open("http://localhost:9999");
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=login]").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void allEmpty() {
        open("http://localhost:9999");
        $("[data-test-id=action-login]").click();
        $("[data-test-id=password]").shouldHave(text("Поле обязательно для заполнения"));
        $("[data-test-id=login]").shouldHave(text("Поле обязательно для заполнения"));
    }
}
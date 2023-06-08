import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DataTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void validActive() {
        var regUser = DataGenirator.regUser("active");
        $("[data-test-id=login] input").setValue(regUser.getLogin());
        $("[data-test-id=password] input").setValue(regUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("h2.heading_theme_alfa-on-white").shouldHave(text("Личный кабинет"));
    }

    @Test
    void validBlocked() {
        var regUser = DataGenirator.regUser("blocked");
        $("[data-test-id=login] input").setValue(regUser.getLogin());
        $("[data-test-id=password] input").setValue(regUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка"));
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void invalidLogin() {
        var regUser = DataGenirator.regUser("active");
        $("[data-test-id=login] input").setValue(DataGenirator.randomLogin());
        $("[data-test-id=password] input").setValue(regUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка"));
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void invalidPassword() {
        var regUser = DataGenirator.regUser("active");
        $("[data-test-id=login] input").setValue(regUser.getLogin());
        $("[data-test-id=password] input").setValue(DataGenirator.randomPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка"));
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void noPassword() {
        var regUser = DataGenirator.regUser("active");
        $("[data-test-id=login] input").setValue(regUser.getLogin());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=password]").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void noLogin() {
        var regUser = DataGenirator.regUser("active");
        $("[data-test-id=password] input").setValue(regUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=login]").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void allEmpty() {
        $("[data-test-id=action-login]").click();
        $("[data-test-id=password]").shouldHave(text("Поле обязательно для заполнения"));
        $("[data-test-id=login]").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void noRerUser() {
        var user = DataGenirator.user("active");
        $("[data-test-id=login] input").setValue(DataGenirator.randomLogin());
        $("[data-test-id=password] input").setValue(DataGenirator.randomPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка"));
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }
}
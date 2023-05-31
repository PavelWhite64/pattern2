import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenirator {
    private final static String active = "active";
    private final static String blocked = "blocked";
    private static Gson gson = new Gson();
    private static Faker faker = new Faker(new Locale("en"));
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static void registrationUsers(Info userData) {
        given()
                .spec(requestSpec)
                .body(gson.toJson(userData))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static Info genirateValidActiv() {
        Info info = new Info(faker.name().username(), faker.internet().password(true), active);
        registrationUsers(info);
        return info;
    }

    public static Info genirateValidBlocked() {
        Info info = new Info(faker.name().username(), faker.internet().password(true), blocked);
        registrationUsers(info);
        return info;
    }

    public static Info genirateInvalidLogin() {
        String password = faker.internet().password(true);
        Info invalid = new Info(faker.name().username(), password, active);
        registrationUsers(invalid);
        return new Info(faker.name().username(), password, active);
    }

    public static Info generateInvalidPassword() {
        String login = faker.name().username();
        Info invalid = new Info(login, faker.internet().password(true), active);
        registrationUsers(invalid);
        return new Info(login, faker.internet().password(true), active);
    }
}
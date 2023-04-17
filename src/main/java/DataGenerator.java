
import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DataGenerator {
    @UtilityClass
    public static class Registration {
        public static User generateUser() {
            Faker faker = new Faker();
            return new User(
                    faker.name().username(),
                    faker.internet().password(),
                    faker.bool().bool() ? "active" : "blocked"
            );
        }
    }
}

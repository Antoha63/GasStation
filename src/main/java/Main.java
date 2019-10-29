import entities.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repositories.UserRepository;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-data-context.xml");

        UserRepository userRepository = context.getBean(UserRepository.class);

        User user = new User();

        user.setLogin("2");
        user.setPassword("2");
        user.setName("Anton");
        user.setAverage(3);

        userRepository.save(user);
    }
}

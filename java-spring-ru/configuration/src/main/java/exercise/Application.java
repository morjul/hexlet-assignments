package exercise;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import  org.springframework.beans.factory.annotation.Autowired;

import exercise.model.User;
import exercise.component.UserProperties;

@SpringBootApplication
@RestController
public class Application {

    // Все пользователи
    private List<User> users = Data.getUsers();

    // BEGIN
    @Autowired // Аннотация нужна для автоподстановки объекта
    private UserProperties userInfo;

    @GetMapping("/admins")
    public List<String> admins() {
        List<String> adminsNames = new LinkedList<>();
        List<String> adminsEmails = userInfo.getAdmins();
        for (String adminEmail : adminsEmails) {
            for(User user: users) {
                if (user.getEmail().equals(adminEmail)) {
                    if (!adminsNames.contains(user.getName())) {
                        adminsNames.add(user.getName());
                        break;
                    }
                }
            }
        }
        return adminsNames.stream().sorted().toList();
    }
    // END

    @GetMapping("/users")
    public List<User> index() {
        return users;
    }

    @GetMapping("/users/{id}")
    public Optional<User> show(@PathVariable Long id) {
        var user = users.stream()
            .filter(u -> u.getId() == id)
            .findFirst();
        return user;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

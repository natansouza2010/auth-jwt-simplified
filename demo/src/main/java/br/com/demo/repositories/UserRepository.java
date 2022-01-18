package br.com.demo.repositories;

import br.com.demo.entities.User;
import br.com.demo.enums.Roles;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRepository {

    private final ArrayList<User> users = new ArrayList<>();

    public UserRepository(){
        this.users.add( new User("1a", "Geraldo", "geraldo@email.com", "123", Roles.ROLE_ADMIN));
        this.users.add( new User("2b", "Ronaldo", "ronaldo@email.com", "123", Roles.ROLE_USER));
        this.users.add( new User("3c", "Juliana", "juliana@email.com", "123", Roles.ROLE_USER));
    }

    public List<User> findAll(){
        return this.users;
    }

    public User findByEmail(String email){
        return this.users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
    }

    public User findById(String id){
        return this.users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst().orElse(null);
    }

    public Roles getRoleById(String id) {

        return findById(id).getRole();

    }
}

package br.com.demo.auth;

import br.com.demo.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
@Service
public class DetailsService {


    public UserPrincipal create(User user) {

        List<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority(user.getRole().toString()));


        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}

package ch.berawan.springrest.security.service;

import ch.berawan.springrest.security.data.entity.User;
import ch.berawan.springrest.security.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User dbUser = this.userRepository.findByUsername(username);

        if (dbUser != null) {
            Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();

            for (String role : dbUser.getRoles()) {
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantedAuthorities.add(authority);
            }

            org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
                    dbUser.getUsername(), dbUser.getPassword(), grantedAuthorities);
            return user;
        }
        else {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
    }

}
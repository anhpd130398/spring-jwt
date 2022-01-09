package demo.service;

import demo.entities.UserBO;
import demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserBO> user = repository.findByUserNames(username);
        if (user != null) {
            for (UserBO userList : user) {
                return new User(userList.getUsername(), userList.getPassword(), new ArrayList<>());
            }

        }
        throw new UsernameNotFoundException("null");

    }
}

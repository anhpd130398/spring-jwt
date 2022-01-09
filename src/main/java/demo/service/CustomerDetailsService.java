package demo.service;

import demo.entities.Role;
import demo.entities.UserBO;
import demo.repository.RoleRepo;
import demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserBO user = repository.findByUserName(username);
        List<SimpleGrantedAuthority> listRole = new ArrayList<>();
        if (user != null) {
            String[] roles = user.getRoleId().split(",");
            for (String roleCode : roles) {
                Role role = roleRepo.findRoleByCode(roleCode.trim());
                listRole.add(new SimpleGrantedAuthority(role.getName()));
            }
            return new User(user.getUsername(), user.getPassword(), listRole);
        }
        throw new UsernameNotFoundException("User not found with the name " + username);

    }
}

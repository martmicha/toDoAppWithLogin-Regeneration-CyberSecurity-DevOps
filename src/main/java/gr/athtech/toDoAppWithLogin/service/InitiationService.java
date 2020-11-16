package gr.athtech.toDoAppWithLogin.service;

import gr.athtech.toDoAppWithLogin.model.security.Role;
import gr.athtech.toDoAppWithLogin.model.security.User;
import gr.athtech.toDoAppWithLogin.repository.RoleRepository;
import gr.athtech.toDoAppWithLogin.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InitiationService {

    static final String ADMIN_USERNAME = "admin";
    static final String ADMIN_PASSWORD = "admin";

    UserRepository userRepository;
    RoleRepository roleRepository;

    public InitiationService(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void initiateDatabase(){
        User user = new User();
        user.setUsername(ADMIN_USERNAME);
        //encrypt password
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
        String encodedPassword = bCryptPasswordEncoder.encode(ADMIN_PASSWORD);
        user.setPassword(encodedPassword);
        //create role for user
        List<Role> allRoles = new ArrayList<>();
        Role adminRole = new Role();
        adminRole.setId((long) 1);
        adminRole.setRole("ADMIN");
        allRoles.add(adminRole);
        Role userRole = new Role();
        userRole.setId((long) 2);
        userRole.setRole("USER");
        allRoles.add(userRole);
        roleRepository.saveAll(allRoles);
        user.setRoles(allRoles);
        userRepository.save(user);
    }
}

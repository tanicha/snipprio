package com.multiversebackend.snipprio.service;

import com.multiversebackend.snipprio.exception.ResourceNotFoundException;
import com.multiversebackend.snipprio.model.User;
import com.multiversebackend.snipprio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public String authenticateUser(User user) throws ResourceNotFoundException {
        //create another object using the given encryption dependency
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        //finds the user passed in by email
        Optional<User> currentUser = userRepository.findById(user.getEmail());
        //if the user is present, we assign their info to dbUser
        if (currentUser.isPresent()) {
            User dbUser = currentUser.get();
            //if the currentUser's password is the same as the database User encrypted password, we show authenticated
            if (bcrypt.matches(user.getPassword(), dbUser.getPassword())) {
                return "Authenticated User";
            } else {
                return "Incorrect Password";
            }
        }
        //if the user is not found at all before the password check, the exception is thrown
        throw new ResourceNotFoundException("No user is found with this username!");
    }

    public String addUser(User user) {
        //building encryption for password
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        //create new variable to hold the users encrypted password
        String encryptedPassword = bcrypt.encode(user.getPassword());
        //call the user again to reassign the encrypted password, not the actual plaintext password
        user.setPassword(encryptedPassword);

        User savedUser = userRepository.save(user);
        return savedUser.getEmail() + " has been added to the database!";
    }

}

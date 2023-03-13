package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }

    public int createUser(User user) {
        // Generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        // Hash the user's password with the generated salt
        String hashedPassword = hashService.getHashedValue(user.getPassword(), Base64.getEncoder().encodeToString(salt));

        // Store the hashed password and salt in the User object
        user.setPassword(hashedPassword);
        user.setSalt(Base64.getEncoder().encodeToString(salt));

        // Insert the user into the database
        return userMapper.insert(user);
    }


}

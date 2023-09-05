package main.java.marketplace.service;

import main.java.marketplace.model.User;
import main.java.marketplace.exception.UserExistsException;
import main.java.marketplace.exception.UserNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class UserService {
    private Map<String, User> users = new HashMap<>();

    public void createUser(User user) throws UserExistsException {
        if (users.containsKey(user.getName()) || users.containsValue(user)) {
            throw new UserExistsException("User with the same username or email already exists.");
        }
        users.put(user.getName(), user);
        System.out.println("User created successfully.");
    }

    public User getUser(String userId) throws UserNotFoundException {
        User user = users.get(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found.");
        }
        return user;
    }

    public void updateUser(User updatedUser) throws UserNotFoundException {
        if (!users.containsKey(updatedUser.getName())) {
            throw new UserNotFoundException("User not found.");
        }
        users.put(updatedUser.getName(), updatedUser);
        System.out.println("User updated successfully.");
    }

    public void deleteUser(String userId) throws UserNotFoundException {
        if (users.remove(userId) == null) {
            throw new UserNotFoundException("User not found.");
        }
        System.out.println("User deleted successfully.");
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
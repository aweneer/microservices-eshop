package swa.eshop.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swa.eshop.user.repository.UserRepository;
import swa.eshop.user.model.User;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserService implements IUserService {

    final Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(User user) {
        logger.log(Level.INFO, "Service event: createUser");
        return userRepository.save(user);
    }

    public Iterable<User> createUsers(Iterable<User> users) {
        logger.log(Level.INFO, "Service event: createUsers");
        return userRepository.saveAll(users);
    }

    @Override
    public User updateUser(User user) {
        logger.log(Level.INFO, "Service event: updateUser");
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        logger.log(Level.INFO, "Service event: getUserById");
        return userRepository.findById(id);
    }

    public Iterable<User> getUsers() {
        logger.log(Level.INFO, "Service event: getUsers");
        return userRepository.findAll();
    }

    public User deleteUserById(Long id) {
        logger.log(Level.INFO, "Service event: deleteUserById");
        if (!userRepository.existsById(id)) return null;
        User user = userRepository.findById(id).get();
        userRepository.delete(user);
        return user;
    }

    public void deleteUser(User user) {
        logger.log(Level.INFO, "Service event: deleteUser");
        userRepository.delete(user);
    }
}

package swa.eshop.user.service;

import org.springframework.stereotype.Service;
import swa.eshop.user.model.User;

@Service
public interface IUserService {
    public User createUser(User user);
    public User updateUser(User user);
}

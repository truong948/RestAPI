package vn.hoidanit.springrestwithai.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.springrestwithai.model.User;
import vn.hoidanit.springrestwithai.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public User findById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        return this.userRepository.save(user);
    }

    public User create(User user) {
        return this.userRepository.save(user);
    }

    public User update(Long id, User user) {
        User currentUser = this.findById(id);
        if (currentUser != null) {
            currentUser.setName(user.getName());
            currentUser.setEmail(user.getEmail());
            if (user.getPassword() != null) {
                currentUser.setPassword(user.getPassword());
            }
            if (user.getAge() != null) {
                currentUser.setAge(user.getAge());
            }
            if (user.getAddress() != null) {
                currentUser.setAddress(user.getAddress());
            }
            if (user.getGender() != null) {
                currentUser.setGender(user.getGender());
            }
            if (user.getAvatar() != null) {
                currentUser.setAvatar(user.getAvatar());
            }
            return this.userRepository.save(currentUser);
        }
        return null;
    }

    public boolean deleteById(Long id) {
        if (this.userRepository.existsById(id)) {
            this.userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existsById(Long id) {
        return this.userRepository.existsById(id);
    }
}

package vn.hoidanit.springrestwithai.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.springrestwithai.exception.DuplicateResourceException;
import vn.hoidanit.springrestwithai.exception.ResourceNotFoundException;
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
        return this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    public User save(User user) {
        return this.create(user);
    }

    public User create(User user) {
        // Kiểm tra duplicate id (nếu có truyền)
        if (user.getId() != null && this.userRepository.existsById(user.getId())) {
            throw new DuplicateResourceException("User", "id", user.getId());
        }

        // Kiểm tra duplicate email
        if (user.getEmail() != null && this.userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("User", "email", user.getEmail());
        }

        return this.userRepository.save(user);
    }

    public User update(Long id, User user) {
        // Tìm user, nếu không tồn tại sẽ throw ResourceNotFoundException
        User currentUser = this.findById(id);

        // Kiểm tra nếu đổi email mới bị trùng với user khác
        if (user.getEmail() != null && !user.getEmail().equals(currentUser.getEmail())
                && this.userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("User", "email", user.getEmail());
        }

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

    public void deleteById(Long id) {
        if (!this.userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        this.userRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return this.userRepository.existsById(id);
    }
}

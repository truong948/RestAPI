package vn.hoidanit.springrestwithai.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.springrestwithai.model.User;
import vn.hoidanit.springrestwithai.repository.UserRepository;

@Service
public class UserService {
    // Khởi tạo thủ công bằng new (chưa dùng Dependency Injection / @Autowired)
    private UserRepository userRepository = new UserRepository();

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public User findById(Long id) {
        return this.userRepository.findById(id);
    }

    public User save(User user) {
        return this.userRepository.save(user);
    }

    public User update(Long id, User user) {
        return this.userRepository.update(id, user);
    }

    public boolean deleteById(Long id) {
        return this.userRepository.deleteById(id);
    }
}

package vn.hoidanit.springrestwithai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.hoidanit.springrestwithai.model.User;
import vn.hoidanit.springrestwithai.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 1. GET /users - lấy tất cả users
    @GetMapping
    public List<User> getAllUsers() {
        return this.userService.findAll();
    }

    // 2. GET /users/{id} - lấy user theo id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return this.userService.findById(id);
    }

    // 3. POST /users - tạo user mới
    @PostMapping
    public User createUser(@RequestBody User user) {
        return this.userService.save(user);
    }

    // 4. PUT /users/{id} - cập nhật user
    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        return this.userService.update(id, user);
    }

    // 5. DELETE /users/{id} - xóa user
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        boolean isDeleted = this.userService.deleteById(id);
        if (isDeleted) {
            return "User with id = " + id + " deleted successfully";
        }
        return "User with id = " + id + " not found";
    }
}

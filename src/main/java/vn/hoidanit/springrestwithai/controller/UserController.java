package vn.hoidanit.springrestwithai.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 1. GET /users - 200 OK
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = this.userService.findAll();
        return ResponseEntity.ok(users);
    }

    // 2. GET /users/{id} - 200 OK nếu tìm thấy, 404 Not Found nếu không
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = this.userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // 3. POST /users - 201 Created với header Location
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = this.userService.create(user);
        URI location = URI.create("/users/" + createdUser.getId());
        return ResponseEntity.created(location).body(createdUser);
    }

    // 4. PUT /users/{id} - 200 OK nếu tìm thấy, 404 Not Found nếu không
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        User updatedUser = this.userService.update(id, user);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    // 5. DELETE /users/{id} - 204 No Content nếu tìm thấy, 404 Not Found nếu không
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        if (!this.userService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        this.userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

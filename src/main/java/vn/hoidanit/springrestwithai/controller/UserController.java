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

import vn.hoidanit.springrestwithai.model.ApiResponse;
import vn.hoidanit.springrestwithai.model.User;
import vn.hoidanit.springrestwithai.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 1. GET /users - Lấy tất cả người dùng
    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = this.userService.findAll();
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách người dùng thành công", users));
    }

    // 2. GET /users/{id} - Lấy người dùng theo id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable("id") Long id) {
        User user = this.userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.notFound("Không tìm thấy người dùng với id = " + id));
        }
        return ResponseEntity.ok(ApiResponse.success("Lấy thông tin người dùng thành công", user));
    }

    // 3. POST /users - Tạo người dùng mới
    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
        if (user.getId() != null && this.userService.existsById(user.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.conflict("Người dùng với id = " + user.getId() + " đã tồn tại"));
        }
        User createdUser = this.userService.create(user);
        URI location = URI.create("/users/" + createdUser.getId());
        return ResponseEntity.created(location)
                .body(ApiResponse.created("Tạo mới người dùng thành công", createdUser));
    }

    // 4. PUT /users/{id} - Cập nhật người dùng theo id
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        User updatedUser = this.userService.update(id, user);
        if (updatedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.notFound("Không tìm thấy người dùng với id = " + id));
        }
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thông tin người dùng thành công", updatedUser));
    }

    // 5. DELETE /users/{id} - Xóa người dùng theo id
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable("id") Long id) {
        if (!this.userService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.notFound("Không tìm thấy người dùng với id = " + id));
        }
        this.userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

package vn.hoidanit.springrestwithai.repository;

import java.util.ArrayList;
import java.util.List;
import vn.hoidanit.springrestwithai.model.User;

public class UserRepository {
    // Khởi tạo ArrayList lưu dữ liệu trong memory
    private List<User> userList = new ArrayList<>();
    private Long nextId = 4L;

    // Khởi tạo sẵn 3 user mẫu
    public UserRepository() {
        userList.add(new User(1L, "Hỏi Dân IT", "hoidanit@gmail.com"));
        userList.add(new User(2L, "Eric", "eric@gmail.com"));
        userList.add(new User(3L, "Harry", "harry@gmail.com"));
    }

    // 1. Lấy tất cả user
    public List<User> findAll() {
        return userList;
    }

    // 2. Tìm user theo id
    public User findById(Long id) {
        for (User user : userList) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    // 3. Thêm user mới
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(nextId++);
        }
        userList.add(user);
        return user;
    }

    // 4. Cập nhật user theo id
    public User update(Long id, User user) {
        User existingUser = findById(id);
        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            return existingUser;
        }
        return null;
    }

    // 5. Xóa user theo id
    public boolean deleteById(Long id) {
        User user = findById(id);
        if (user != null) {
            userList.remove(user);
            return true;
        }
        return false;
    }
}


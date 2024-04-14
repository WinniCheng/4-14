package hkmu.comps380f.onlinebookstoreapp.dao;

import hkmu.comps380f.onlinebookstoreapp.exception.BookUserNotFound;
import hkmu.comps380f.onlinebookstoreapp.model.BookUser;
import hkmu.comps380f.onlinebookstoreapp.model.UserRole;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserManagementService {
    @Resource
    private BookUserRepository buRepo;

    @Transactional
    public List<BookUser> getBookUsers() {
        return buRepo.findAll();
    }

    @Transactional
    public BookUser getBookUser(String username)
            throws BookUserNotFound {
        BookUser bookUser = buRepo.findById(username).orElse(null);
        if (bookUser == null) {
            throw new BookUserNotFound(username);
        }
        return bookUser;
    }

    @Transactional(rollbackFor = BookUserNotFound.class)
    public void updateBookUser(String username, String password, String fullname, String email, String address, String[] roles)
            throws BookUserNotFound {
        BookUser bookUser = buRepo.findById(username).orElse(null);
        if (bookUser == null) {
            throw new BookUserNotFound(username);
        }
        bookUser.setUsername(username);
        bookUser.setPassword(password);
        bookUser.setFullname(fullname);
        bookUser.setEmail(email);
        bookUser.setAddress(address);

        bookUser.getRoles().clear();

        for (String role : roles) {
            UserRole userRole = new UserRole();
            userRole.setUsername(username);
            userRole.setRole(role);
            userRole.setUser(bookUser);

            if (userRole.getRole() != null && userRole.getRole().length() > 0) {
                bookUser.getRoles().add(userRole);
            }
        }

        buRepo.save(bookUser);
    }

    @Transactional
    public void delete(String username) {
        BookUser bookUser = buRepo.findById(username).orElse(null);
        if (bookUser == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        buRepo.delete(bookUser);
    }

    @Transactional
    public void createBookUser(String username, String password, String fullname, String email, String address, String[] roles) {
        BookUser user = new BookUser(username, password, fullname, email, address, roles);
        buRepo.save(user);
    }

    @Transactional
    public void registration(String username, String password, String fullname, String email, String address, String[] roles) {
        BookUser user = new BookUser(username, password, fullname, email, address, roles);
        buRepo.save(user);
    }
}

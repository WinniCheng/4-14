package hkmu.comps380f.onlinebookstoreapp.controller;

import hkmu.comps380f.onlinebookstoreapp.dao.UserManagementService;
import hkmu.comps380f.onlinebookstoreapp.exception.BookUserNotFound;
import hkmu.comps380f.onlinebookstoreapp.model.BookUser;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserManagementController {
    @Resource
    UserManagementService umService;

    @GetMapping({"", "/", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("bookUsers", umService.getBookUsers());
        return "user/list";
    }

    public static class Form {
        private String username;
        private String password;
        private String fullname;
        private String email;
        private String address;
        private String[] roles;

        // getters and setters for all properties
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }


        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("user/add", "bookUser", new Form());
    }

    @PostMapping("/create")
    public String create(Form form) throws IOException {
        umService.createBookUser(form.getUsername(),
                form.getPassword(), form.getFullname(), form.getEmail(), form.getAddress(), form.getRoles());
        return "redirect:/user/list";
    }

    @GetMapping("/register")
    public ModelAndView registration() {
        return new ModelAndView("user/registration", "bookUser", new Form());
    }

    @PostMapping("/register")
    public String registration(Form form) throws IOException {
        umService.registration(form.getUsername(),
                form.getPassword(), form.getFullname(), form.getEmail(), form.getAddress(), form.getRoles());
        return "redirect:/book/list";
    }

    @GetMapping("/delete/{username}")
    public String deleteBook(@PathVariable("username") String username) {
        umService.delete(username);
        return "redirect:/user/list";
    }

    @GetMapping("/edit/{username}")
    public ModelAndView showUserEdit(@PathVariable("username") String username,
                                 Principal principal, HttpServletRequest request)
            throws BookUserNotFound {
        BookUser bookuser = umService.getBookUser(username);
        String loggedInUsername = principal.getName();

        if ((bookuser == null || !request.isUserInRole("ROLE_ADMIN")) && !loggedInUsername.equals(username)) {
            return new ModelAndView(new RedirectView("/book/list", true));
        }
        ModelAndView modelAndView = new ModelAndView("user/edit");
        modelAndView.addObject("user", bookuser);
        UserManagementController.Form bookUserForm = new UserManagementController.Form();

        bookUserForm.setUsername(bookuser.getUsername());
        bookUserForm.setPassword(bookuser.getPassword());
        bookUserForm.setFullname(bookuser.getFullname());
        bookUserForm.setEmail(bookuser.getEmail());
        bookUserForm.setAddress(bookuser.getAddress());

        modelAndView.addObject("bookUser", bookUserForm);
        return modelAndView;
    }


    @PostMapping("/edit/{username}")
    public String userEdit(@PathVariable("username") String username, UserManagementController.Form form,
                       Principal principal, HttpServletRequest request)
            throws IOException, BookUserNotFound {
        BookUser bookuser = umService.getBookUser(username);
        String loggedInUsername = principal.getName();
        if ((bookuser == null || !request.isUserInRole("ROLE_ADMIN")) && !loggedInUsername.equals(username)) {
            return "redirect:/user/list";
        }
        String password = "{noop}"+form.getPassword();
        umService.updateBookUser(username, password,form.getFullname(), form.getEmail(), form.getAddress(), form.getRoles());
        if (loggedInUsername.equals(username)){
            return "redirect:/book/list";
        }
        return "redirect:/user/list";
    }
}
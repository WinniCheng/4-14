package hkmu.comps380f.onlinebookstoreapp.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class BookUser {
    @Id
    private String username;

    private String password;

    private String fullname;

    private String email;

    private String address;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> roles = new ArrayList<>();

    public BookUser() {}

    public BookUser(String username, String password, String fullname, String email, String address, String[] roles) {
        this.username = username;
        this.password = "{noop}" + password;
        this.fullname = fullname;
        this.email = email;
        this.address = address;

        for (String role : roles) {
            this.roles.add(new UserRole(this, role));
        }
    }

    // getters and setters of all properties

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

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }
}
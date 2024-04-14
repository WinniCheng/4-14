package hkmu.comps380f.onlinebookstoreapp.dao;

import hkmu.comps380f.onlinebookstoreapp.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}

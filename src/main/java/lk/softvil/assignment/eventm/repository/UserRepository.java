package lk.softvil.assignment.eventm.repository;

import lk.softvil.assignment.eventm.model.entity.User;
import lk.softvil.assignment.eventm.model.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {


    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    // Check if email exists (for registration)
    boolean existsByEmail(String email);

    // Custom query to find users with admin role
    @Query("SELECT u FROM User u WHERE u.role = 'ADMIN'")
    List<User> findAllAdmins();

    // Find users hosting events in a location
    @Query("SELECT DISTINCT e.host FROM Event e WHERE e.location = :location")
    List<User> findHostsByLocation(@Param("location") String location);

    @Query("SELECT u FROM User u WHERE u.role = :role")
    Page<User> findByRole(@Param("role") UserRole role, Pageable pageable);

    // Alternative implementation if you need both list and page versions
    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findAllByRole(@Param("role") UserRole role);
}
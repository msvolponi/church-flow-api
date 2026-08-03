package br.com.volps.church_flow_api.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.volps.church_flow_api.entity.User;
import br.com.volps.church_flow_api.enums.Role;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    List<User> findByRoleNot(Role role);

}

package br.com.volps.church_flow_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.volps.church_flow_api.entity.FollowUp;

@Repository
public interface FollowUpRepository extends JpaRepository<FollowUp, Long> {

    @EntityGraph(attributePaths = { "person", "responsible" })
    List<FollowUp> findByPersonIdOrderByDateDesc(Long personId);

    @Override
    @EntityGraph(attributePaths = { "person", "responsible" })
    Optional<FollowUp> findById(Long id);
}

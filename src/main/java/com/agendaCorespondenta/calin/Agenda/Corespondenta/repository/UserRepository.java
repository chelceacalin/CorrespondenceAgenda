package com.agendaCorespondenta.calin.Agenda.Corespondenta.repository;

import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

	@Query("select u from UserEntity u where u.email=?1")
	Optional<UserEntity> findByEmail(String email);
}

package kz.nmbet.betradar.dao.repository;

import kz.nmbet.betradar.dao.domain.entity.GlUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<GlUser, Integer> {

	GlUser findByEmail(String email);
	
	GlUser findByCashierId(Integer cashierId);
}
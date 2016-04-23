package kz.nmbet.betradar.dao.repository;

import java.util.List;

import kz.nmbet.betradar.dao.domain.entity.GlTournamentEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlTournamentEntityRepository extends JpaRepository<GlTournamentEntity, Integer> {

	GlTournamentEntity findByTournamentId(Integer id);
	
	List<GlTournamentEntity> findByCategoryId(Integer categoryId);

	
}
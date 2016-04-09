package kz.nmbet.betradar.dao.repository;

import kz.nmbet.betradar.dao.domain.entity.GlUniqueTournamentEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlUniqueTournamentEntityRepository extends JpaRepository<GlUniqueTournamentEntity, Integer> {

	
}
package kz.nmbet.betradar.dao.repository;

import kz.nmbet.betradar.dao.domain.entity.GlTeamEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlTeamEntityRepository extends JpaRepository<GlTeamEntity, Integer> {

	GlTeamEntity findBySuperTeamId(Integer id);
	
}
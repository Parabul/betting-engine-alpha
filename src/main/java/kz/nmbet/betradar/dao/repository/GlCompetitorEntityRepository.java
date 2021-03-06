package kz.nmbet.betradar.dao.repository;

import kz.nmbet.betradar.dao.domain.entity.GlCompetitorEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlCompetitorEntityRepository extends JpaRepository<GlCompetitorEntity, Integer> {

	GlCompetitorEntity findByOutrightIdAndSuperIdAndTeamId(Integer id, Integer superId, Integer teamId);

	GlCompetitorEntity findByMatchIdAndSuperIdAndTeamId(Integer id, Integer superTeamId, Integer teamId);
}
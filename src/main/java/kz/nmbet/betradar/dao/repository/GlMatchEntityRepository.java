package kz.nmbet.betradar.dao.repository;

import java.util.List;

import kz.nmbet.betradar.dao.domain.entity.GlMatchEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GlMatchEntityRepository extends JpaRepository<GlMatchEntity, Integer> {

	GlMatchEntity findByMatchId(Long matchId);

}
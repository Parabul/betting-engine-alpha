package kz.nmbet.betradar.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kz.nmbet.betradar.dao.domain.entity.GlSportEntity;

@Repository
public interface GlSportEntityRepository extends JpaRepository<GlSportEntity, Integer> {

	GlSportEntity findBySportId(int id);

}
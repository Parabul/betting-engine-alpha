package kz.nmbet.betradar.dao.repository;

import kz.nmbet.betradar.dao.domain.entity.GlOutrightOddEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlOutrightOddEntityRepository extends JpaRepository<GlOutrightOddEntity, Long> {

	
}
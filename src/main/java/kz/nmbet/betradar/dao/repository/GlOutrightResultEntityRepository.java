package kz.nmbet.betradar.dao.repository;

import kz.nmbet.betradar.dao.domain.entity.GlOutrightResultEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlOutrightResultEntityRepository extends JpaRepository<GlOutrightResultEntity, Integer> {

	
}
package kz.nmbet.betradar.dao.repository;

import kz.nmbet.betradar.dao.domain.entity.GlMatchOddEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlMatchOddEntityRepository extends JpaRepository<GlMatchOddEntity, Integer> {

	
}
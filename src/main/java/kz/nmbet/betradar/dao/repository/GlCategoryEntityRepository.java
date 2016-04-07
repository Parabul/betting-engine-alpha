package kz.nmbet.betradar.dao.repository;

import kz.nmbet.betradar.dao.domain.entity.GlCategoryEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlCategoryEntityRepository extends JpaRepository<GlCategoryEntity, Long> {

	GlCategoryEntity findByCategoryId(long id);
}
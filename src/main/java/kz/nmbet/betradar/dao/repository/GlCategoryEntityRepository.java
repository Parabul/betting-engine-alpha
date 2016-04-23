package kz.nmbet.betradar.dao.repository;

import java.util.List;

import kz.nmbet.betradar.dao.domain.entity.GlCategoryEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GlCategoryEntityRepository extends JpaRepository<GlCategoryEntity, Integer> {

	GlCategoryEntity findByCategoryId(Long categoryid);

	@Query("select r from GlCategoryEntity r where r.sport.id = :sportId ")
	List<GlCategoryEntity> getBySportId(@Param("sportId") Integer sportId);
}
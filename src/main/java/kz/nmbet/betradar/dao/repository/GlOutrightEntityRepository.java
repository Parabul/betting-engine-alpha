package kz.nmbet.betradar.dao.repository;

import java.util.List;

import kz.nmbet.betradar.dao.domain.entity.GlOutrightEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlOutrightEntityRepository extends
		JpaRepository<GlOutrightEntity, Integer> {

	GlOutrightEntity findByOutrightId(Integer id);

	List<GlOutrightEntity> findByCategoryId(Integer categoryId);
}
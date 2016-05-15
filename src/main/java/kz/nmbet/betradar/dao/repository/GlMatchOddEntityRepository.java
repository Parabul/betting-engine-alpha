package kz.nmbet.betradar.dao.repository;

import kz.nmbet.betradar.dao.domain.entity.GlMatchEntity;
import kz.nmbet.betradar.dao.domain.entity.GlMatchOddEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlMatchOddEntityRepository extends JpaRepository<GlMatchOddEntity, Integer> {

	List<GlMatchOddEntity> findByMatchIdOrderByMatchOddsTypeAsc(Integer id);

	List<GlMatchOddEntity> findByIdIn(List<Integer> ids);
}
package kz.nmbet.betradar.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kz.nmbet.betradar.dao.domain.entity.GlMatchLiveOddField;

@Repository
public interface GlMatchLiveOddFieldRepository extends JpaRepository<GlMatchLiveOddField, Integer> {

	List<GlMatchLiveOddField> findByLiveOddMatchIdAndActiveTrueAndLiveOddActiveTrueOrderByLiveOddIdAscViewIndexAsc(Integer id);
	
	List<GlMatchLiveOddField> findByIdInAndActiveTrue(List<Integer> ids);
	
	List<GlMatchLiveOddField> findByIdAndActiveTrue(Integer id);
}
package kz.nmbet.betradar.dao.repository;

import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kz.nmbet.betradar.dao.domain.entity.GlMatchLiveOdd;

@Repository
public interface GlMatchLiveOddRepository extends JpaRepository<GlMatchLiveOdd, Integer> {

	List<GlMatchLiveOdd> findByActiveTrue();

	List<GlMatchLiveOdd> findByMatchIdIn(Integer[] ids);
	
	List<GlMatchLiveOdd> findByMatchId(Integer ids);

	@Fetch(FetchMode.SUBSELECT)
	@Query("select r from GlMatchLiveOdd r  left join fetch r.oddFields where r.match.id = :id and r.active=true")
	List<GlMatchLiveOdd> findByMatchIdAndActiveTrue(@Param("id") Integer id);

	@Modifying
	@Query(nativeQuery = true, value = "update gl_match_live_odd set active=false where extract('epoch' from (current_timestamp-check_date)) > 20")
	int updateInactive();



}
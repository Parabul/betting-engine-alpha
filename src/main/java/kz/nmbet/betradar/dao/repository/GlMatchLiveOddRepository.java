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

	@Fetch(FetchMode.SUBSELECT)
	@Query("select r from GlMatchLiveOdd r left join fetch r.oddFields where r.match.id in :ids ")
	List<GlMatchLiveOdd> getByMatchIdIn(@Param("ids") Integer[] ids);

	@Fetch(FetchMode.SUBSELECT)
	@Query("select r from GlMatchLiveOdd r left join fetch r.oddFields where r.match.id = :id ")
	List<GlMatchLiveOdd> getByMatchId(@Param("id") Integer id);

	@Query("select r from GlMatchLiveOdd r left join fetch r.oddFields where r.match.matchId = :id ")
	List<GlMatchLiveOdd> getByRemoteMatchId(@Param("id") Long id);

	@Fetch(FetchMode.SUBSELECT)
	@Query("select r from GlMatchLiveOdd r  left join fetch r.oddFields where r.match.id = :id and r.active=true")
	List<GlMatchLiveOdd> findByMatchIdAndActiveTrue(@Param("id") Integer id);

	@Modifying
	@Query(nativeQuery = true, value = "update gl_match_live_odd set active=false where extract('epoch' from (current_timestamp-check_date)) > 20")
	int updateInactive();

	@Modifying
	@Query(nativeQuery = true, value = "update gl_match_live_odd set active=true where gl_match_id = ?")
	int aliveReceivedNative(Integer matchId);

	@Modifying
	@Query(value = "update GlMatchLiveOdd o set o.active=true, o.check_date=current_timestamp  where o.match.id in ( :matchIds )")
	void aliveReceived(@Param("matchIds") List<Integer> matchIds);

}
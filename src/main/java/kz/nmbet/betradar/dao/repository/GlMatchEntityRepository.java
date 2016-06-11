package kz.nmbet.betradar.dao.repository;

import java.util.List;

import kz.nmbet.betradar.dao.domain.entity.GlMatchEntity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GlMatchEntityRepository extends JpaRepository<GlMatchEntity, Integer> {

	GlMatchEntity findByMatchId(Long matchId);

	List<GlMatchEntity> findByTournamentId(Integer tournamentId);

	@Fetch(FetchMode.SUBSELECT)
	@Query("select r from GlMatchEntity r left join fetch r.odds  left join fetch r.competitors where r.tournament.category.id = :id")
	List<GlMatchEntity> getByCategoryId(@Param("id") Integer id);

	@Fetch(FetchMode.SUBSELECT)
	@Query("select r from GlMatchEntity r left join fetch r.odds  left join fetch r.competitors where r.tournament.id = :id")
	List<GlMatchEntity> getByTournamentId(@Param("id") Integer id);

	@Fetch(FetchMode.SUBSELECT)
	@Query("select r from GlMatchEntity r left join fetch r.odds  left join fetch r.competitors where r.tournament.id in :ids")
	List<GlMatchEntity> getByTournamentIds(@Param("ids") Integer[] ids);

	@Fetch(FetchMode.SUBSELECT)
	@Query("select r from GlMatchEntity r left join fetch r.competitors where r.tournament.category.sport.id = :id")
	List<GlMatchEntity> getBySportId(@Param("id") Integer id);

	@Query(nativeQuery = true, value = "select id, event_date, is_active, match_id, title, gl_tournament_id from gl_match_entity where id in (select distinct gl_match_id from gl_match_live_odd live_odd where live_odd.active = true)")
	List<GlMatchEntity> getLiveMatches();

}
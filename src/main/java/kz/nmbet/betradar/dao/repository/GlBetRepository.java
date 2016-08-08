package kz.nmbet.betradar.dao.repository;

import java.util.List;

import kz.nmbet.betradar.dao.domain.entity.GlBet;
import kz.nmbet.betradar.dao.domain.entity.GlUser;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GlBetRepository extends JpaRepository<GlBet, Integer> {

	List<GlBet> findByOwner(GlUser owner, Pageable page);

	List<GlBet> findByOutrightOddEntityIsNotNullAndWinsIsNull();

	@Query(nativeQuery = true, value = "select distinct bet.* from gl_bet bet left join mm_bet_live_odd_fields mm on mm.bet_id=bet.id left join gl_match_live_odd_field odd_field on odd_field.id=mm.live_odd_field_id where odd_field.outcome is not null and wins is null")
	List<GlBet> getBetsWithLiveResult();
	
	@Query(nativeQuery = true, value = "select distinct bet.* from gl_bet bet left join mm_bet_match_odds mm on mm.bet_id=bet.id left join gl_match_odd_entity odd on odd.id=mm.match_odd_id where odd.odd_result is not null and wins is null")
	List<GlBet> getBetsWithPreMatchResult();

	List<GlBet> findByOwnerOrderByIdDesc(GlUser user, Pageable page);
	
}
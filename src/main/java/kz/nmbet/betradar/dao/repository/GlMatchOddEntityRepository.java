package kz.nmbet.betradar.dao.repository;

import kz.nmbet.betradar.dao.domain.entity.GlMatchOddEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GlMatchOddEntityRepository extends JpaRepository<GlMatchOddEntity, Integer> {

	List<GlMatchOddEntity> findByMatchIdOrderByMatchOddsTypeAsc(Integer id);

	List<GlMatchOddEntity> findByIdIn(List<Integer> ids);

	@Modifying
	@Query(nativeQuery = true, value = "update gl_match_odd_entity set odd_result = sub.wins from ( select wins, odd_id from v_ready_results) sub where id=sub.odd_id")
	int updateWithResult();

}
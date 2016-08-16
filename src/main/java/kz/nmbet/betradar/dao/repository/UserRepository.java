package kz.nmbet.betradar.dao.repository;

import kz.nmbet.betradar.dao.domain.entity.GlUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<GlUser, Integer> {

	GlUser findByEmail(String email);

	GlUser findByCashierId(Integer cashierId);

	@Modifying
	@Query("update GlUser u set defaultBetAmount = ?1 where u.email = ?2 ")
	void updateDefaultBetAmount(Double amount, String email);

	@Modifying
	@Query("update GlUser u set fastBetEnabled = ?1 where u.email = ?2 ")
	void updateFastBetEnabled(Boolean enabled, String email);
}
package kz.nmbet.betradar.dao.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kz.nmbet.betradar.dao.domain.entity.GlCashbox;
import kz.nmbet.betradar.dao.domain.entity.GlUser;

@Repository
public interface GlCashboxRepository extends JpaRepository<GlCashbox, Integer> {

	List<GlCashbox> findByEnabledTrue();
}
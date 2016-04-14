package kz.nmbet.betradar.dao.repository;

import kz.nmbet.betradar.dao.domain.entity.GlBet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlBetRepository extends JpaRepository<GlBet, Integer> {

}
package kz.nmbet.betradar.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kz.nmbet.betradar.dao.domain.entity.GlMatchLiveOdd;

@Repository
public interface GlMatchLiveOddRepository extends JpaRepository<GlMatchLiveOdd, Integer> {

}
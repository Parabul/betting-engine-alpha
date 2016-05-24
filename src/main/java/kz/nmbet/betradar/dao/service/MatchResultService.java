package kz.nmbet.betradar.dao.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import kz.nmbet.betradar.dao.domain.entity.GlBet;
import kz.nmbet.betradar.dao.domain.entity.GlMatchLiveOddField;
import kz.nmbet.betradar.dao.domain.entity.GlMatchOddEntity;
import kz.nmbet.betradar.dao.repository.GlBetRepository;
import kz.nmbet.betradar.dao.repository.GlMatchOddEntityRepository;

@Service
public class MatchResultService {

	private static final Logger logger = LoggerFactory.getLogger(MatchResultService.class);

	@Autowired
	private GlBetRepository betRepository;

	@Autowired
	private GlMatchOddEntityRepository oddEntityRepository;

	@Autowired
	private RemoteStoreService remoteStoreService;

	@Scheduled(fixedRate = 30000)
	@Transactional
	public void updateWithResult() {
		oddEntityRepository.updateWithResult();
		
	}

	@Scheduled(fixedRate = 5000)
	@Transactional
	public void updateLiveOddWthResult() {
		List<GlBet> bets = betRepository.getBetsWithLiveResult();
		if (bets != null && bets.size() > 0)
			for (GlBet bet : bets) {
				Boolean wins = null;
				for (GlMatchLiveOddField liveOdd : bet.getLiveOdds()) {
					if (liveOdd.getOutcome() != null) {
						if (wins == null)
							wins = liveOdd.getOutcome();
						else
							wins = wins && liveOdd.getOutcome();
					}
				}
				if (wins != null) {
					bet.setWins(wins);
					if (wins) {
						bet.setWinAmount(bet.getBetAmount() * bet.getOddValue());
					} else {
						bet.setWinAmount(0.0d);
					}
					bet = betRepository.save(bet);
					remoteStoreService.updateWithResult(bet);
				}

			}
	}

	@Scheduled(fixedRate = 5000)
	@Transactional
	public void updatePreMatchOddWthResult() {
		List<GlBet> bets = betRepository.getBetsWithPreMatchResult();
		if (bets != null && bets.size() > 0)
			for (GlBet bet : bets) {
				Boolean wins = null;
				for (GlMatchOddEntity odd : bet.getMatchOddEntity()) {
					if (odd.getOddResult() != null) {
						if (wins == null)
							wins = odd.getOddResult();
						else
							wins = wins && odd.getOddResult();
					}
				}
				if (wins != null) {
					bet.setWins(wins);
					if (wins) {
						bet.setWinAmount(bet.getBetAmount() * bet.getOddValue());
					} else {
						bet.setWinAmount(0.0d);
					}
					bet = betRepository.save(bet);
					remoteStoreService.updateWithResult(bet);
				}

			}
	}

}

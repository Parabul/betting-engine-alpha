package kz.nmbet.betradar.dao.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import kz.nmbet.betradar.dao.domain.entity.GlBet;
import kz.nmbet.betradar.dao.domain.entity.GlOutrightOddEntity;
import kz.nmbet.betradar.dao.domain.entity.GlOutrightResultEntity;
import kz.nmbet.betradar.dao.repository.GlBetRepository;

@Service
public class ResultsCheckService {

	private static final Logger logger = LoggerFactory.getLogger(ResultsCheckService.class);

	@Autowired
	private GlBetRepository betRepository;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

	@Scheduled(fixedRate = 5000)
	@Transactional
	public void checkOutrightOdds() {
		logger.info("Start check on " + sdf.format(new Date()));
		List<GlBet> waitingBets = betRepository.findByOutrightOddEntityIsNotNullAndWinsIsNull();
		for (GlBet bet : waitingBets) {
			checkBet(bet);
		}
		logger.info("End check on " + sdf.format(new Date()));
	}

	private boolean hasMininalPlace(Set<GlOutrightResultEntity> results, Integer teamId, Integer place) {
		for (GlOutrightResultEntity result : results) {
			if (result.getTeamId().equals(teamId)) {
				if (result.getResult() <= place) {
					return true;
				}
			}
		}
		return false;
	}

	@Transactional
	public GlBet checkBet(GlBet bet) {
		if (bet != null) {
			bet.setCheckDate(new Date());

			GlOutrightOddEntity odd = bet.getOutrightOddEntity();
			Set<GlOutrightResultEntity> results = bet.getOutrightOddEntity().getOutright().getResults();
			if (results != null && results.size() > 0) {
				logger.info("Start update result for bet #: " + bet.getId());
				boolean win = false;
				switch (odd.getOddsType()) {
				case championship_outrights:
					win = hasMininalPlace(results, odd.getTeamId(), 1);
					break;
				case podium_finish:
					win = hasMininalPlace(results, odd.getTeamId(), 3);
					break;
				case short_term_outrights:
					win = hasMininalPlace(results, odd.getTeamId(), 1);
					break;
				}
				bet.setWins(win);
				if (win) {
					bet.setWinAmount(bet.getBetAmount() * odd.getValue());
				}

			}
			return betRepository.save(bet);
		} else
			throw new IllegalArgumentException("Номер ставки не указан не верно. Либо данные устарели");
	}

}

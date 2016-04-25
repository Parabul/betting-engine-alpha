package kz.nmbet.betradar.dao.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import kz.nmbet.betradar.dao.domain.entity.GlMatchEntity;
import kz.nmbet.betradar.dao.domain.entity.GlTournamentEntity;
import kz.nmbet.betradar.dao.domain.views.ActiveCategory;
import kz.nmbet.betradar.dao.repository.GlCategoryEntityRepository;
import kz.nmbet.betradar.dao.repository.GlMatchEntityRepository;
import kz.nmbet.betradar.dao.repository.GlTournamentEntityRepository;
import kz.nmbet.betradar.web.beans.MatchInfoBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PublicMatchService {

	private static final Logger logger = LoggerFactory.getLogger(PublicMatchService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private GlMatchEntityRepository matchEntityRepository;

	@Autowired
	private GlCategoryEntityRepository categoryEntityRepository;

	@Autowired
	private GlTournamentEntityRepository tournamentEntityRepository;

	@Transactional
	public Map<String, List<ActiveCategory>> getActiveCategories() {
		Map<String, List<ActiveCategory>> sports = new HashMap<String, List<ActiveCategory>>();
		List<ActiveCategory> result = new ArrayList<ActiveCategory>();
		jdbcTemplate.query(ActiveCategory.query, (resultSet, rowNum) -> result.add(new ActiveCategory(resultSet, rowNum)));

		for (ActiveCategory activeCategory : result) {
			String sportName = activeCategory.getSportName();
			if (!sports.containsKey(sportName)) {
				sports.put(sportName, new ArrayList<ActiveCategory>());
			}
			sports.get(sportName).add(activeCategory);
		}
		return sports;
	}

	@Transactional
	public List<MatchInfoBean> getMatchesByTournament(Integer id) {
		List<MatchInfoBean> matches = new ArrayList<MatchInfoBean>();
		logger.info("getMatchesByCategory start ");
		Collection<GlMatchEntity> matchEntities = new LinkedHashSet<GlMatchEntity>(matchEntityRepository.getByTournamentId(id));
		logger.info("getMatchesByCategory obtain data " + matchEntities.size() + " end");
		for (GlMatchEntity match : matchEntities) {
			MatchInfoBean matchInfoBean = new MatchInfoBean(match);
			if (!matchInfoBean.isEmpty())
				matches.add(matchInfoBean);

		}
		logger.info("getMatchesByCategory loop end");
		return matches;
	}

	@Transactional
	public GlTournamentEntity getTournament(Integer id) {
		return tournamentEntityRepository.findOne(id);
	}

}

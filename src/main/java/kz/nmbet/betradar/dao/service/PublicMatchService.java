package kz.nmbet.betradar.dao.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import kz.nmbet.betradar.dao.domain.entity.GlCategoryEntity;
import kz.nmbet.betradar.dao.domain.entity.GlMatchEntity;
import kz.nmbet.betradar.dao.domain.entity.GlTournamentEntity;
import kz.nmbet.betradar.dao.domain.views.ActiveCategory;
import kz.nmbet.betradar.dao.repository.GlCategoryEntityRepository;
import kz.nmbet.betradar.dao.repository.GlMatchEntityRepository;
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
	public List<MatchInfoBean> getMatchesByCategory(Integer id) {
		List<MatchInfoBean> matches = new ArrayList<MatchInfoBean>();
		GlCategoryEntity category = categoryEntityRepository.findOne(id);
		for (GlTournamentEntity tournament : category.getTournaments()) {
			for (GlMatchEntity match : tournament.getMatches()) {
				MatchInfoBean matchInfoBean = new MatchInfoBean(match);
				if (!matchInfoBean.isEmpty())
					matches.add(matchInfoBean);
			}
		}
		return matches;
	}

	@Transactional
	public GlCategoryEntity getCategory(Integer id) {
		return categoryEntityRepository.findOne(id);
	}

}

package kz.nmbet.betradar.dao.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import kz.nmbet.betradar.dao.domain.entity.GlOutrightEntity;
import kz.nmbet.betradar.dao.domain.views.ActiveOutright;
import kz.nmbet.betradar.dao.domain.views.OutrightOdd;
import kz.nmbet.betradar.dao.repository.GlBetRepository;
import kz.nmbet.betradar.dao.repository.GlOutrightEntityRepository;
import kz.nmbet.betradar.web.beans.OutrightInfo;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PublicOutrightService {

	private static final Logger logger = LoggerFactory.getLogger(PublicOutrightService.class);

	@Autowired
	private GlOutrightEntityRepository outrightEntityRepository;

	@Autowired
	private GlBetRepository betRepository;

	@Autowired
	private DSLContext dslContext;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Transactional
	public List<OutrightInfo> findAll() {
		logger.debug("PublicOutrightService findAll");
		return outrightEntityRepository.findAll().stream().map(elt -> new OutrightInfo(elt)).collect(Collectors.toList());

	}

	@Transactional
	public Map<String, List<ActiveOutright>> getActiveOutrights() {
		Map<String, List<ActiveOutright>> sports = new HashMap<String, List<ActiveOutright>>();
		List<ActiveOutright> result = new ArrayList<ActiveOutright>();
		jdbcTemplate.query(ActiveOutright.query, (resultSet, rowNum) -> result.add(new ActiveOutright(resultSet, rowNum)));

		for (ActiveOutright activeCategory : result) {
			String sportName = activeCategory.getSportName();
			if (!sports.containsKey(sportName)) {
				sports.put(sportName, new ArrayList<ActiveOutright>());
			}
			sports.get(sportName).add(activeCategory);
		}
		return sports;
	}

	@Transactional
	public List<OutrightOdd> findOutrightOdds(Integer id) {
		List<OutrightOdd> result = new ArrayList<OutrightOdd>();
		jdbcTemplate.query(OutrightOdd.query, new Object[]{id}, (resultSet, rowNum) -> result.add(new OutrightOdd(resultSet, rowNum)));
		return result;
	}

	@Transactional
	public GlOutrightEntity findOne(Integer id) {
		return outrightEntityRepository.findOne(id);
	}

}

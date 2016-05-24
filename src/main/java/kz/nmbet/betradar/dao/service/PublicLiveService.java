package kz.nmbet.betradar.dao.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kz.nmbet.betradar.dao.domain.entity.GlMatchLiveOdd;
import kz.nmbet.betradar.dao.domain.views.MatchDetails;
import kz.nmbet.betradar.dao.repository.GlCompetitorEntityRepository;
import kz.nmbet.betradar.dao.repository.GlMatchEntityRepository;
import kz.nmbet.betradar.dao.repository.GlMatchLiveOddRepository;
import kz.nmbet.betradar.utils.TextsEntityUtils;
import kz.nmbet.betradar.web.beans.MatchInfoBean;

@Service
public class PublicLiveService {

	private static final Logger logger = LoggerFactory.getLogger(PublicLiveService.class);

	@Autowired
	private GlMatchEntityRepository matchEntityRepository;

	@Autowired
	private TeamService teamService;

	@Autowired
	private GlCompetitorEntityRepository competitorEntityRepository;

	@Autowired
	private GlMatchLiveOddRepository liveOddRepository;

	@Autowired
	private TextsEntityUtils textsEntityUtils;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Transactional(readOnly = true)
	public List<MatchDetails> getLiveMathes() {
		List<MatchDetails> result = new ArrayList<MatchDetails>();
		jdbcTemplate.query(MatchDetails.live_query, new Object[] {},
				(resultSet, rowNum) -> result.add(new MatchDetails(resultSet, rowNum)));
		return result;
	}

	@Transactional
	public Map<Integer, MatchInfoBean> getActiveOdds(Integer[] matchIds) {
		Map<Integer, MatchInfoBean> matchInfos = new HashMap<Integer, MatchInfoBean>();

		List<GlMatchLiveOdd> liveOdds = liveOddRepository.findByMatchIdInAndActiveTrue(matchIds);
		for (GlMatchLiveOdd liveOdd : liveOdds) {
			Integer key = liveOdd.getMatch().getId();
			Collections.sort(liveOdd.getOddFields());
			if (!matchInfos.containsKey(key)) {
				matchInfos.put(key, new MatchInfoBean(liveOdd.getMatch(), true));
				matchInfos.get(key).setLiveOdds(new ArrayList<>());
			}
			matchInfos.get(key).getLiveOdds().add(liveOdd);
		}
		for (Entry<Integer, MatchInfoBean> matchInfo : matchInfos.entrySet()) {
			Collections.sort(matchInfo.getValue().getLiveOdds());
		}
		return matchInfos;
	}

	@Transactional
	public MatchInfoBean getActiveOdds(Integer matchId) {
		MatchInfoBean matchInfo = null;

		Collection<GlMatchLiveOdd> liveOdds = new LinkedHashSet<GlMatchLiveOdd>(
				liveOddRepository.findByMatchIdAndActiveTrue(matchId));

		for (GlMatchLiveOdd liveOdd : liveOdds) {
			Collections.sort(liveOdd.getOddFields());
			if (matchInfo == null) {
				matchInfo = new MatchInfoBean(liveOdd.getMatch(), true);
				matchInfo.setLiveOdds(new ArrayList<>());
			}
			matchInfo.getLiveOdds().add(liveOdd);
		}
		if (matchInfo != null && matchInfo.getLiveOdds() != null)
			Collections.sort(matchInfo.getLiveOdds());
		return matchInfo;
	}

	@Transactional
	public Map<Integer, MatchInfoBean> getActiveOdds() {
		Map<Integer, MatchInfoBean> matchInfos = new HashMap<Integer, MatchInfoBean>();

		List<GlMatchLiveOdd> liveOdds = liveOddRepository.findByActiveTrue();
		for (GlMatchLiveOdd liveOdd : liveOdds) {
			Integer key = liveOdd.getMatch().getId();
			if (!matchInfos.containsKey(key)) {
				matchInfos.put(key, new MatchInfoBean(liveOdd.getMatch(), true));
				matchInfos.get(key).setLiveOdds(new ArrayList<>());
			}
			matchInfos.get(key).getLiveOdds().add(liveOdd);
		}
		return matchInfos;
	}
}

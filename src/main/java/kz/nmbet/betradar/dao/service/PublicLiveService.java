package kz.nmbet.betradar.dao.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kz.nmbet.betradar.dao.domain.entity.GlMatchLiveOdd;
import kz.nmbet.betradar.dao.domain.views.MatchDetails;
import kz.nmbet.betradar.dao.repository.GlMatchLiveOddRepository;
import kz.nmbet.betradar.web.beans.MatchInfoBean;

@Service
public class PublicLiveService {

	private static final Logger logger = LoggerFactory.getLogger(PublicLiveService.class);

	@Autowired
	private GlMatchLiveOddRepository liveOddRepository;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Transactional(readOnly = true)
	public Map<String, List<MatchDetails>> getLiveMathes() {
		List<MatchDetails> results = new ArrayList<MatchDetails>();
		jdbcTemplate.query(MatchDetails.live_query, new Object[] {},
				(resultSet, rowNum) -> results.add(new MatchDetails(resultSet, rowNum)));
		Map<String, List<MatchDetails>> resultMap = new HashMap<>();
		for (MatchDetails result : results) {
			String key = result.getSport();
			if (!resultMap.containsKey(key)) {
				resultMap.put(key, new ArrayList<MatchDetails>());
			}
			resultMap.get(key).add(result);
		}

		return resultMap;
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

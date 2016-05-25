package kz.nmbet.betradar.dao.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import kz.nmbet.betradar.dao.domain.views.OutrightOdd;
import kz.nmbet.betradar.dao.domain.views.StatView;
import kz.nmbet.betradar.web.beans.StatBean;

@Service
public class AdminService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Transactional
	public StatBean getStatData() {
		StatBean statBean = new StatBean();
		List<StatView> sportResult = new ArrayList<StatView>();
		jdbcTemplate.query(StatView.sports_query, new Object[] {},
				(resultSet, rowNum) -> sportResult.add(new StatView(resultSet, rowNum)));
		statBean.setSports(sportResult);

		List<StatView> betResult = new ArrayList<StatView>();
		jdbcTemplate.query(StatView.bet_types_query, new Object[] {},
				(resultSet, rowNum) -> betResult.add(new StatView(resultSet, rowNum)));
		statBean.setBetTypes(betResult);

		List<StatView> amountsResult = new ArrayList<StatView>();
		jdbcTemplate.query(StatView.bet_amounts_query, new Object[] {},
				(resultSet, rowNum) -> amountsResult.add(new StatView(resultSet, rowNum)));
		statBean.setBetAmounts(amountsResult);

		List<StatView> winResult = new ArrayList<StatView>();
		jdbcTemplate.query(StatView.win_amounts_query, new Object[] {},
				(resultSet, rowNum) -> winResult.add(new StatView(resultSet, rowNum)));
		statBean.setWinAmounts(winResult);
		return statBean;
	}
}

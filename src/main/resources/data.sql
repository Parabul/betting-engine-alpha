drop view IF EXISTS v_active_categories;
CREATE OR REPLACE VIEW v_active_categories AS 
 SELECT 
 	sport.id sport_id,
 	COALESCE(sport.name_ru, sport.name_en) AS sport_name,
    (COALESCE(category.name_ru, category.name_en)::text || ', '::text) || COALESCE(tournament.name_ru, tournament.name_en)::text AS group_name,
    tournament.id AS tournament_id
   FROM gl_sport_entity sport
     LEFT JOIN gl_category_entity category ON category.gl_sport_id = sport.id
     LEFT JOIN gl_tournament_entity tournament ON tournament.gl_category_id = category.id
     LEFT JOIN gl_match_entity match ON match.gl_tournament_id = tournament.id
  WHERE match.event_date > current_timestamp
  GROUP BY sport.id, category.id, tournament.id
 HAVING count(match.id) > 0;
 
 
create or replace view v_active_outrights as
select 
	COALESCE(sport.name_ru,sport.name_en) as sport_name,
	COALESCE(category.name_ru,category.name_en)||', '||outright.event_info as group_name,	
	outright.id as outright_id	
from gl_sport_entity sport 	  
	left join gl_category_entity category on category.gl_sport_id=sport.id
	left join gl_outright_entity outright on outright.gl_category_id=category.id
	where outright.id is not null;
	
create or replace view v_outright_odds as
select  
	outright.id outright_id,
	odd.id odd_id, 
	odd.old_value,
	odd.value, 
	odd.odds_type, 
	outright.event_date,
	outright.event_info,
	COALESCE(team.name_ru,team.name_en) as team_name

from gl_outright_odd_entity odd
	left join gl_outright_entity outright on outright.id=odd.gl_outright_id
	left join gl_competitor_entity competitor on outright.id=competitor.gl_outright_id and competitor.team_id=odd.team_id
	left join gl_team_entity team on team.id=competitor.gl_team_id
	order by odd.value;
	
CREATE OR REPLACE VIEW public.v_matches AS 
 SELECT sport.id AS sport_id,
    (((((((((((match.id || '| '::text) || COALESCE(sport.name_ru, sport.name_en)::text) || ', '::text) || COALESCE(category.name_ru, category.name_en)::text) || ', '::text) || COALESCE(tournament.name_ru, tournament.name_en)::text) || ', '::text) || to_char(match.event_date, 'DD.MM.YYYY HH24:MI'::text)) || ', '::text) || COALESCE(home_team.name_ru, home_team.name_en, home_competitor.title)::text) || ' - '::text) || COALESCE(away_team.name_ru, away_team.name_en, away_competitor.title)::text AS description,
    match.id AS match_id
   FROM gl_sport_entity sport
     LEFT JOIN gl_category_entity category ON category.gl_sport_id = sport.id
     LEFT JOIN gl_tournament_entity tournament ON tournament.gl_category_id = category.id
     LEFT JOIN gl_match_entity match ON match.gl_tournament_id = tournament.id
     LEFT JOIN gl_competitor_entity home_competitor ON match.id = home_competitor.gl_match_id AND 'HOME'::text = home_competitor.team_type::text
     LEFT JOIN gl_team_entity home_team ON home_team.id = home_competitor.gl_team_id
     LEFT JOIN gl_competitor_entity away_competitor ON match.id = away_competitor.gl_match_id AND 'AWAY'::text = away_competitor.team_type::text
     LEFT JOIN gl_team_entity away_team ON away_team.id = away_competitor.gl_team_id;

CREATE OR REPLACE VIEW public.v_match_infos AS 
	 SELECT sport.id AS sport_id,
	 match.id  match_id,
	 COALESCE(sport.name_ru, sport.name_en) sport_name,
	 COALESCE(category.name_ru, category.name_en) category_name,
	 COALESCE(tournament.name_ru, tournament.name_en) tournament_name,
	 to_char(match.event_date, 'DD.MM.YYYY HH24:MI'::text) event_date,
	 COALESCE(home_team.name_ru, home_team.name_en, home_competitor.title)  home_team_name,
	 COALESCE(away_team.name_ru, away_team.name_en, away_competitor.title) away_team_name
 

   FROM gl_sport_entity sport
     LEFT JOIN gl_category_entity category ON category.gl_sport_id = sport.id
     LEFT JOIN gl_tournament_entity tournament ON tournament.gl_category_id = category.id
     LEFT JOIN gl_match_entity match ON match.gl_tournament_id = tournament.id
     LEFT JOIN gl_competitor_entity home_competitor ON match.id = home_competitor.gl_match_id AND 'HOME'::text = home_competitor.team_type::text
     LEFT JOIN gl_team_entity home_team ON home_team.id = home_competitor.gl_team_id
     LEFT JOIN gl_competitor_entity away_competitor ON match.id = away_competitor.gl_match_id AND 'AWAY'::text = away_competitor.team_type::text
     LEFT JOIN gl_team_entity away_team ON away_team.id = away_competitor.gl_team_id
     where  match.id is not null;
     
CREATE OR REPLACE view  v_ready_results as 
select odd.out_come=bet_result.outcome as wins, odd.id odd_id from gl_match_odd_entity odd 
	left join gl_match_bet_result_entity bet_result on bet_result.odds_type=odd.match_odds_type and bet_result.gl_match_id=odd.gl_match_id and (bet_result.special_bet_value=odd.special_bet_value or (odd.special_bet_value is null and bet_result.special_bet_value is null))
	where bet_result.id is not null and bet_result.void_factor is null and odd_result is null;     
	
CREATE OR REPLACE VIEW public.v_live_match_infos AS 
 SELECT sport.id AS sport_id,
    match.id AS match_id,
    COALESCE(sport.name_ru, sport.name_en) AS sport_name,
    COALESCE(category.name_ru, category.name_en) AS category_name,
    COALESCE(tournament.name_ru, tournament.name_en) AS tournament_name,
    to_char(match.event_date, 'DD.MM.YYYY HH24:MI'::text) AS event_date,
    COALESCE(home_team.name_ru, home_team.name_en, home_competitor.title) AS home_team_name,
    COALESCE(away_team.name_ru, away_team.name_en, away_competitor.title) AS away_team_name
   FROM gl_sport_entity sport
     LEFT JOIN gl_category_entity category ON category.gl_sport_id = sport.id
     LEFT JOIN gl_tournament_entity tournament ON tournament.gl_category_id = category.id
     LEFT JOIN gl_match_entity match ON match.gl_tournament_id = tournament.id
     LEFT JOIN gl_competitor_entity home_competitor ON match.id = home_competitor.gl_match_id AND 'HOME'::text = home_competitor.team_type::text
     LEFT JOIN gl_team_entity home_team ON home_team.id = home_competitor.gl_team_id
     LEFT JOIN gl_competitor_entity away_competitor ON match.id = away_competitor.gl_match_id AND 'AWAY'::text = away_competitor.team_type::text
     LEFT JOIN gl_team_entity away_team ON away_team.id = away_competitor.gl_team_id
  WHERE match.id IS NOT NULL and extract('epoch' from (current_timestamp-match.live_check_date)) < 20 and live_started=true;

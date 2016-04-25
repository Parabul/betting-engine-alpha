CREATE OR REPLACE VIEW v_active_categories AS 
 SELECT COALESCE(sport.name_ru, sport.name_en) AS sport_name,
    (COALESCE(category.name_ru, category.name_en)::text || ', '::text) || COALESCE(tournament.name_ru, tournament.name_en)::text AS group_name,
    tournament.id AS tournament_id
   FROM gl_sport_entity sport
     LEFT JOIN gl_category_entity category ON category.gl_sport_id = sport.id
     LEFT JOIN gl_tournament_entity tournament ON tournament.gl_category_id = category.id
     LEFT JOIN gl_match_entity match ON match.gl_tournament_id = tournament.id
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

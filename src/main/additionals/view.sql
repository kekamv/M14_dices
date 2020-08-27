DROP VIEW IF EXISTS ranking_view;

CREATE VIEW ranking_view AS SELECT p.*, (SELECT AVG(g.game_score)*100 FROM games g WHERE g.player_id = p.id) AS success_rate FROM players p;

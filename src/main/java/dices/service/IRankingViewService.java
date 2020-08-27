package dices.service;

import dices.model.RankingView;

import java.util.List;

public interface IRankingViewService {

    List<RankingView> findTopOrderBySuccessRateDesc();

    List<RankingView> findTopByOrderBySuccessRateAsc();

    List<RankingView> findAllRankingView();
}

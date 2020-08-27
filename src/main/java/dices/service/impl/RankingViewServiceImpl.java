package dices.service.impl;

import dices.model.RankingView;
import dices.repository.RankingViewRepository;
import dices.service.IRankingViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingViewServiceImpl implements IRankingViewService {

@Autowired
    RankingViewRepository rankingViewRepository;

    //finds players with max success rate
    @Override
    public List<RankingView> findTopOrderBySuccessRateDesc() {
        return rankingViewRepository.findTopByOrderBySuccessRateDesc();
    }
    //finds players min success rate
    @Override
    public List<RankingView> findTopByOrderBySuccessRateAsc() {
        return rankingViewRepository.findTopByOrderBySuccessRateAsc();
    }

    @Override
    public List<RankingView> findAllRankingView(){
    return rankingViewRepository.findAll();
    }
}

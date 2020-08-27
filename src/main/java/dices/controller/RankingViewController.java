package dices.controller;

import dices.service.IRankingViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players")
public class RankingViewController {

    @Autowired
    IRankingViewService rankingViewService;

    @GetMapping("/ranking/winner")
    public ResponseEntity getBestRanking(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(rankingViewService.findTopOrderBySuccessRateDesc());
    }

    @GetMapping("/ranking/loser")
    public ResponseEntity getWorseRanking(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(rankingViewService.findTopByOrderBySuccessRateAsc());
    }
}

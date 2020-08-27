package dices.repository;

import dices.model.RankingView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RankingViewRepository extends JpaRepository<RankingView,Long> {

    @Query(value = "SELECT rw.* FROM ranking_view rw WHERE rw.success_rate=(SELECT MAX(rw1.success_rate) FROM ranking_view rw1)", nativeQuery = true)
    List<RankingView> findTopByOrderBySuccessRateDesc();

    @Query(value = "SELECT rw.* FROM ranking_view rw WHERE rw.success_rate=(SELECT MIN(rw1.success_rate) FROM ranking_view rw1)", nativeQuery = true)
    List<RankingView> findTopByOrderBySuccessRateAsc();
}

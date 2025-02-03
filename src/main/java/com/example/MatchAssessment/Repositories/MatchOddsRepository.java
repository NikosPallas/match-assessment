package com.example.MatchAssessment.Repositories;

import com.example.MatchAssessment.Entities.MatchOdds;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchOddsRepository extends JpaRepository<MatchOdds, Long> {

    List<MatchOdds> findByMatchId(Long matchId);
}

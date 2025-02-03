package com.example.MatchAssessment.Services;

import com.example.MatchAssessment.Entities.MatchOdds;
import com.example.MatchAssessment.Repositories.MatchOddsRepository;
import com.example.MatchAssessment.Repositories.MatchRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;


@Service
public class MatchOddsService {

    private final MatchOddsRepository matchOddsRepository;
    private final MatchRepository matchRepository;

    public MatchOddsService(MatchOddsRepository matchOddsRepository, MatchRepository matchRepository) {
        this.matchOddsRepository = matchOddsRepository;
        this.matchRepository = matchRepository;
    }

    //Get all MatchOdds
    public List<MatchOdds> getAllMatchOdds() {
        return matchOddsRepository.findAll();
    }

    public List<MatchOdds> getMatchOddsByMatchId(Long matchId) {
        return matchOddsRepository.findByMatchId(matchId);
    }

    //Add MatchOdds to a Match (Ensure Match Exists)
    public Optional<MatchOdds> addMatchOdds(Long matchId, MatchOdds odds) {
        return matchRepository.findById(matchId).map(match -> {
            odds.setMatch(match); // Set the existing match
            return matchOddsRepository.save(odds);
        });
    }

    //Update MatchOdds (Preserve the existing match)
    public Optional<MatchOdds> updateMatchOdds(Long id, MatchOdds newOdds) {
        return matchOddsRepository.findById(id).map(existingOdds -> {
            existingOdds.setSpecifier(newOdds.getSpecifier());
            existingOdds.setOdd(newOdds.getOdd());
            //DO NOT CHANGE THE MATCH REFERENCE (Keep it as is)
            return matchOddsRepository.save(existingOdds);
        });
    }

    //Delete MatchOdds
    public boolean deleteMatchOdds(Long id) {
        if (matchOddsRepository.existsById(id)) {
            matchOddsRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

package com.example.MatchAssessment.Services;

import com.example.MatchAssessment.Entities.Match;
import com.example.MatchAssessment.Entities.MatchOdds;
import com.example.MatchAssessment.Repositories.MatchOddsRepository;
import com.example.MatchAssessment.Repositories.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

    @Service
    public class MatchService {

        private final MatchRepository matchRepository;
        private final MatchOddsRepository matchOddsRepository;

        public MatchService(MatchRepository matchRepository, MatchOddsRepository matchOddsRepository) {
            this.matchRepository = matchRepository;
            this.matchOddsRepository = matchOddsRepository;
        }

        //Get all matches
        public List<Match> getAllMatches() {
            return matchRepository.findAll();
        }

        //Get match by ID
        public Optional<Match> getMatchById(Long id) {
            return matchRepository.findById(id);
        }

        //Create a match
        public Match createMatch(Match match) {
            // Ensure odds are not null
            if (match.getMatchOdds() == null) {
                match.setMatchOdds(new ArrayList<>());
            }

            // Set the match reference for each MatchOdds
            for (MatchOdds odds : match.getMatchOdds()) {
                odds.setMatch(match);  // Link the Match to each MatchOdds
            }

            // Save the match, which will also save the odds because of CascadeType.ALL
            Match savedMatch = matchRepository.save(match);

            return savedMatch;

        }


        public Optional<Match> updateMatch(Long id, Match matchDetails) {
            return matchRepository.findById(id).map(existingMatch -> {

                // Update basic match details
                existingMatch.setDescription(matchDetails.getDescription());
                existingMatch.setMatchDate(matchDetails.getMatchDate());
                existingMatch.setMatchTime(matchDetails.getMatchTime());
                existingMatch.setTeamA(matchDetails.getTeamA());
                existingMatch.setTeamB(matchDetails.getTeamB());
                existingMatch.setSport(matchDetails.getSport());

                // Ensure MatchOdds list is updated correctly
                if (matchDetails.getMatchOdds() != null) {
                    for (MatchOdds odds : matchDetails.getMatchOdds()) {
                        odds.setMatch(existingMatch);  //Set the match reference
                    }

                    // Remove old odds & add updated ones
                    existingMatch.getMatchOdds().clear();
                    existingMatch.getMatchOdds().addAll(matchDetails.getMatchOdds());
                }

                // Save the updated match
                return matchRepository.save(existingMatch);
            });
        }

        //Delete a match
        public boolean deleteMatch(Long id) {
            if (matchRepository.existsById(id)) {
                matchRepository.deleteById(id);
                return true;
            }
            return false;
        }

        //Get all odds for a match
        public Optional<List<MatchOdds>> getMatchOdds(Long matchId) {
            return matchRepository.findById(matchId).map(Match::getMatchOdds);
        }
}

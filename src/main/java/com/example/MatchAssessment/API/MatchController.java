package com.example.MatchAssessment.API;

import com.example.MatchAssessment.Entities.MatchOdds;
import com.example.MatchAssessment.Services.MatchOddsService;
import com.example.MatchAssessment.Services.MatchService;
import com.example.MatchAssessment.Entities.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @RequestMapping("/api/matches")
    public class MatchController {

        private final MatchService matchService;
        private final MatchOddsService matchOddsService;

        //Only one constructor that's why the @Autowired annotation is redundant
        public MatchController(MatchService matchService, MatchOddsService matchOddsService) {
            this.matchService = matchService;
            this.matchOddsService = matchOddsService;
        }

        //Get all matches
        @GetMapping
        public List<Match> getAllMatches() {
            return matchService.getAllMatches();
        }


        // Get match by ID
        @GetMapping("/{id}")
        public ResponseEntity<Match> getMatchById(@PathVariable Long id) {
            return matchService.getMatchById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        //Create a match
        @PostMapping
        public ResponseEntity<Match> createMatch(@RequestBody Match match) {
            Match createdMatch = matchService.createMatch(match);
            return ResponseEntity.ok(createdMatch);
        }

        //Update a match
        //Attention to provide the body of a complete match and not only the match_odd(specifier and odd)
        @PutMapping("/{id}")
        public ResponseEntity<Match> updateMatch(@PathVariable Long id, @RequestBody Match matchDetails) {
            return matchService.updateMatch(id, matchDetails)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        //5. Delete a match
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
            if (matchService.deleteMatch(id)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();   //TODO: A message to the user that the match does not exist could be useful
        }

        //6. Add MatchOdds to a Match
        @PostMapping("/{matchId}/odds")
        public ResponseEntity<MatchOdds> addMatchOdds(@PathVariable Long matchId, @RequestBody MatchOdds odds) {
             //TODO: Here we could check if there is also a same specifier and avoid doubling it
            return matchOddsService.addMatchOdds(matchId, odds)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        //7. Get all odds for a match
        @GetMapping("/{matchId}/odds")
        public ResponseEntity<List<MatchOdds>> getMatchOdds(@PathVariable Long matchId) {
            return matchService.getMatchOdds(matchId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
    }


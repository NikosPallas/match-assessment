package com.example.MatchAssessment.API;

import com.example.MatchAssessment.Entities.MatchOdds;
import com.example.MatchAssessment.Services.MatchOddsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/match-odds")
public class MatchOddsController {

    private final MatchOddsService matchOddsService;

    public MatchOddsController(MatchOddsService matchOddsService) {
        this.matchOddsService = matchOddsService;
    }

    //Get all MatchOdds
    @GetMapping
    public List<MatchOdds> getAllMatchOdds() {
        return matchOddsService.getAllMatchOdds();
    }
    //Get MatchOdds by MatchId
    //It provides similar functioning as the one in MatchController
    @GetMapping("/match/{matchId}")
    public ResponseEntity<List<MatchOdds>> getMatchOddsByMatchId(@PathVariable Long matchId) {
        List<MatchOdds> matchOddsList = matchOddsService.getMatchOddsByMatchId(matchId);

        if (matchOddsList.isEmpty()) {
            return ResponseEntity.notFound().build();  // Return 404 if no odds exist
        }

        return ResponseEntity.ok(matchOddsList);  // Return all odds of a match
    }

    //Add MatchOdds to a Match
    //It provides similar functioning as the one in MatchController
    @PostMapping("/{matchId}")
    public ResponseEntity<MatchOdds> addMatchOdds(@PathVariable Long matchId, @RequestBody MatchOdds odds) {
        return matchOddsService.addMatchOdds(matchId, odds)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Update MatchOdds based on the odd_id NOT match_id
    @PutMapping("/{id}")
    public ResponseEntity<MatchOdds> updateMatchOdds(@PathVariable Long id, @RequestBody MatchOdds newOdds) {
        return matchOddsService.updateMatchOdds(id, newOdds)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Delete MatchOdds based on the odd_id NOT match_id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatchOdds(@PathVariable Long id) {
        if (matchOddsService.deleteMatchOdds(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

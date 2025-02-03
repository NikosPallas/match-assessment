package com.example.MatchAssessment.MatchControllerTests;

import static org.junit.jupiter.api.Assertions.*;

import com.example.MatchAssessment.API.MatchController;
import com.example.MatchAssessment.Entities.Match;
import com.example.MatchAssessment.Entities.MatchOdds;
import com.example.MatchAssessment.Services.MatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;


class MatchControllerUnitTest {

    @Mock
    private MatchService matchService;

    @InjectMocks
    private MatchController matchController;

    private Match sampleMatch;
    private List<MatchOdds> sampleOddsList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample odds for the match
        sampleOddsList = new ArrayList<>();
        MatchOdds odds1 = new MatchOdds();
        odds1.setId(1L);
        odds1.setSpecifier("1");
        odds1.setOdd(2.5);

        MatchOdds odds2 = new MatchOdds();
        odds2.setId(2L);
        odds2.setSpecifier("X");
        odds2.setOdd(3.1);

        sampleOddsList.add(odds1);
        sampleOddsList.add(odds2);

        // Sample match with odds
        sampleMatch = new Match();
        sampleMatch.setId(1L);
        sampleMatch.setDescription("Premier League - Matchday 10");
        sampleMatch.setTeamA("Liverpool");
        sampleMatch.setTeamB("Manchester United");
        sampleMatch.setSport(Match.Sport.FOOTBALL);
        sampleMatch.setMatchOdds(sampleOddsList);
    }

    // Test Get Match by ID
    @Test
    void testGetMatchById_Success() {
        when(matchService.getMatchById(1L)).thenReturn(Optional.of(sampleMatch));

        ResponseEntity<Match> response = matchController.getMatchById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(Match.Sport.FOOTBALL, response.getBody().getSport());
        assertEquals(2, response.getBody().getMatchOdds().size());
    }

    @Test
    void testGetMatchById_NotFound() {
        when(matchService.getMatchById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Match> response = matchController.getMatchById(1L);

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    // Test Create Match
    @Test
    void testCreateMatch() {
        when(matchService.createMatch(any(Match.class))).thenReturn(sampleMatch);

        ResponseEntity<Match> response = matchController.createMatch(sampleMatch);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(Match.Sport.FOOTBALL, response.getBody().getSport());
        assertEquals(2, response.getBody().getMatchOdds().size());
    }

    // Test Update Match
    @Test
    void testUpdateMatch_Success() {
        when(matchService.updateMatch(eq(1L), any(Match.class))).thenReturn(Optional.of(sampleMatch));

        ResponseEntity<Match> response = matchController.updateMatch(1L, sampleMatch);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(Match.Sport.FOOTBALL, response.getBody().getSport());
        assertEquals(2, response.getBody().getMatchOdds().size());
    }

    @Test
    void testUpdateMatch_NotFound() {
        when(matchService.updateMatch(eq(1L), any(Match.class))).thenReturn(Optional.empty());

        ResponseEntity<Match> response = matchController.updateMatch(1L, sampleMatch);

        assertEquals(404, response.getStatusCode().value());
    }

    // Test Delete Match
    @Test
    void testDeleteMatch_Success() {
        when(matchService.deleteMatch(1L)).thenReturn(true);

        ResponseEntity<Void> response = matchController.deleteMatch(1L);

        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void testDeleteMatch_NotFound() {
        when(matchService.deleteMatch(1L)).thenReturn(false);

        ResponseEntity<Void> response = matchController.deleteMatch(1L);

        assertEquals(404, response.getStatusCode().value());
    }
}
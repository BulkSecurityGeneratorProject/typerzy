package com.mituta.web.rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.mituta.domain.Bet;
import com.mituta.domain.FixtureResult;
import com.mituta.domain.Game;
import com.mituta.domain.Standing;
import com.mituta.domain.Team;
import com.mituta.domain.Tournament;
import com.mituta.domain.User;
import com.mituta.repository.GameRepository;
import com.mituta.service.BetService;
import com.mituta.service.GameService;
import com.mituta.service.TournamentService;
import com.mituta.web.rest.util.PaginationUtil;

@RestController
@RequestMapping("/api")
public class StandingResource {

	@Inject
	TournamentService tournamentService;

	@Inject
	GameService gameService;

	@Inject
	BetService betService;

	@RequestMapping(value = "/standings/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public List<Standing> getStandings(@PathVariable Long id)
			throws URISyntaxException {

		Tournament tournament = tournamentService.findOne(id);
		Set<User> players = tournament.getPlayers();
		List<Game> games = gameService.findForTournament(id);
		List<Standing> points = new ArrayList<>();

		for (User player : players) {
			Standing standing = new Standing();
			standing.setUser(player);
			standing.setPoints(calculatePoints(player, games, tournament));
			points.add(standing);
		}

		points.sort(new StandingComparator());
		return points;
	}

	private static class StandingComparator implements Comparator<Standing> {

		@Override
		public int compare(Standing arg0, Standing arg1) {

			return arg1.getPoints() - arg0.getPoints();
		}

	}

	private Integer calculatePoints(User player, List<Game> games,
			Tournament tournament) {
		int points = 0;
		for (Game game : games) {
			Bet bet = getUserBet(player, betService.getForGame(game));
			if (bet != null && bet.getResult() != null
					&& game.getResult() != null) {
				FixtureResult betResult = bet.getResult();
				FixtureResult gameResult = game.getResult();
				if (gameResult.getHome() != null
						&& gameResult.getAway() != null) {

					if (betResult.getAway() == gameResult.getAway()
							&& betResult.getHome() == gameResult.getHome()) {
						points += tournament.getExactResultPoints();
					} else if (sameWinnerOrDraw(betResult, gameResult)) {
						points += tournament.getResultPoints();
					}
				}

			}
		}
		return points;
	}

	private boolean sameWinnerOrDraw(FixtureResult betResult,
			FixtureResult gameResult) {
		int betResultSign = (int) Math.signum(betResult.getHome()
				- betResult.getAway());
		int gameResultSign = (int) Math.signum(gameResult.getHome()
				- gameResult.getAway());
		return betResultSign == gameResultSign;
	}

	private Bet getUserBet(User player, List<Bet> bets) {
		for (Bet bet : bets) {
			if (bet.getUser().getLogin().equals(player.getLogin())) {
				return bet;
			}
		}
		return null;
	}
}

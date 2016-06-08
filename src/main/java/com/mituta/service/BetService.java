package com.mituta.service;

import com.mituta.domain.Bet;
import com.mituta.domain.Game;
import com.mituta.domain.Tournament;
import com.mituta.domain.User;
import com.mituta.repository.BetRepository;
import com.mituta.repository.FixtureResultRepository;
import com.mituta.security.SecurityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing Bet.
 */
@Service
@Transactional
public class BetService {

	private final Logger log = LoggerFactory.getLogger(BetService.class);

	@Inject
	private UserService userService;
	@Inject
	private BetRepository betRepository;
	
	@Inject
	private TournamentService tournamentService;

	/**
	 * Save a bet.
	 * 
	 * @param bet
	 *            the entity to save
	 * @return the persisted entity
	 */
	public Bet save(Bet bet) {
		log.debug("Request to save Bet : {}", bet);
		bet.setTime(ZonedDateTime.now());

		Optional<User> currentUser = getCurrentUser();
		if(currentUser.isPresent())
		{
			bet.setUser(currentUser.get());
		}
		
		Bet result = betRepository.save(bet);
		return result;
	}

	private Optional<User> getCurrentUser() {
		return userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin());
	}

	/**
	 * Get all the bets.
	 * 
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public List<Bet> findAll() {
		log.debug("Request to get all Bets");
		List<Bet> result = betRepository.findAll();
		
		ZonedDateTime now = ZonedDateTime.now();
		
		for(Bet bet : result )
		{
			hideIfNecessary(now, bet);
		}
		return result;
	}

	private void hideIfNecessary(ZonedDateTime now, Bet bet) {
		
		if( bet.getUser().getLogin().equals(SecurityUtils.getCurrentUserLogin()))
		{
			bet.setHidden(false);
		}
		else
		if( bet.getFixture().getTime().isAfter(now))
		{
			bet.setHidden(true);
			bet.setResult(null);
		}
		else
		{
			bet.setHidden(false);
		}
	}
	
	@Transactional(readOnly = true)
	public List<Bet> getForGame(Game game)
	{
		
		Tournament tournament = tournamentService.findOne( game.getTournament().getId() );
		Set<User > players = tournament.getPlayers();
		List<Bet> bets = betRepository.findByGameId(game.getId());
		List<Bet> results = new ArrayList<>();
		for(User user: players)
		{
			results.add(getBet(game, user, bets));
		}
		
		return results;
		
	}

	private Bet getBet(Game game, User user, List<Bet> bets) {
		
		for( Bet bet : bets)
		{
			if(bet.getUser().equals(user))
			{
				hideIfNecessary(ZonedDateTime.now(), bet);
				bet.setSet(true);
				return bet;
			}
		}
		Bet emptyBet = new Bet();
		emptyBet.setUser(user);
		emptyBet.setSet(false);
		return emptyBet;
		
	}

	/**
	 * Get one bet by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public Bet findOne(Long id) {
		log.debug("Request to get Bet : {}", id);
		Bet bet = betRepository.findOne(id);
		return bet;
	}

	/**
	 * Delete the bet by id.
	 * 
	 * @param id
	 *            the id of the entity
	 */
	public void delete(Long id) {
		log.debug("Request to delete Bet : {}", id);
		betRepository.delete(id);
	}

	public Bet getForCurrentUserAndGame(long game) {
		
		log.debug("Request to get current user Bet for: {}", game);
		List<Bet> bets = betRepository.getforCurrentUserAndGame(game);
		if(bets.isEmpty())
		{
			return null;
		}
		return bets.get(0);
	}
}

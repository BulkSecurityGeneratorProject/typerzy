package com.mituta.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Game.
 */
@Entity
@Table(name = "game")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "time")
    private ZonedDateTime time;

    @ManyToOne
    @JoinColumn(name="home_id")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name="away_id")
    private Team awayTeam;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="TOURNAMENT_ID")
    private Tournament tournament;
    
    @OneToOne
    @JoinColumn(unique = true)
    private FixtureResult result;

    @OneToMany
    private Set<FixtureResult> bets = new HashSet<>();
    

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team team) {
        this.homeTeam = team;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team team) {
        this.awayTeam = team;
    }

    public FixtureResult getResult() {
        return result;
    }

    public void setResult(FixtureResult fixtureResult) {
        this.result = fixtureResult;
    }


    public Set<FixtureResult> getBets() {
		return bets;
	}

	public void setBets(Set<FixtureResult> bets) {
		this.bets = bets;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Game game = (Game) o;
        if(game.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, game.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Game{" +
            "id=" + id +
            ", time='" + time + "'" +
            '}';
    }
}

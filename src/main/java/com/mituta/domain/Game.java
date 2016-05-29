package com.mituta.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

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
    private Team home;

    @ManyToOne
    private Team away;

    @OneToOne
    @JoinColumn(unique = true)
    private FixtureResult result;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

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

    public Team getHome() {
        return home;
    }

    public void setHome(Team team) {
        this.home = team;
    }

    public Team getAway() {
        return away;
    }

    public void setAway(Team team) {
        this.away = team;
    }

    public FixtureResult getResult() {
        return result;
    }

    public void setResult(FixtureResult fixtureResult) {
        this.result = fixtureResult;
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

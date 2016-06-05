package com.mituta.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Tournament.
 */
@Entity
@Table(name = "tournament")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tournament implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "tournament_players",
               joinColumns = @JoinColumn(name="tournaments_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="players_id", referencedColumnName="ID"))
    private Set<User> players = new HashSet<>();

    @OneToMany(mappedBy="tournament")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Game> fixtures = new HashSet<>();

    
    private int resultPoints =1;
    private int exactResultPoints =2;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getPlayers() {
        return players;
    }

    public void setPlayers(Set<User> users) {
        this.players = users;
    }

    public Set<Game> getFixtures() {
        return fixtures;
    }

    public void setFixtures(Set<Game> games) {
        this.fixtures = games;
    }

    public int getResultPoints() {
		return resultPoints;
	}

	public void setResultPoints(int resultPoints) {
		this.resultPoints = resultPoints;
	}

	public int getExactResultPoints() {
		return exactResultPoints;
	}

	public void setExactResultPoints(int exactResultPoints) {
		this.exactResultPoints = exactResultPoints;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tournament tournament = (Tournament) o;
        if(tournament.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tournament.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tournament{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }

	public void addGame(Game game) {
		fixtures.add(game);
	}
}

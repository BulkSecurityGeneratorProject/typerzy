package com.mituta.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Bet.
 */
@Entity
@Table(name = "bet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bet implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(name = "time", nullable = false)
	private ZonedDateTime time;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "user_id", unique = false)
	private User user;

	@ManyToOne
	@NotNull
	private Game fixture;

	@ManyToOne(cascade = CascadeType.ALL)
	@NotNull
	private FixtureResult result;

	@Transient
	private boolean isHidden;
	
	@Transient
	private boolean isSet;


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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Game getFixture() {
		return fixture;
	}

	public void setFixture(Game game) {
		this.fixture = game;
	}

	public FixtureResult getResult() {
		return result;
	}

	public void setResult(FixtureResult fixtureResult) {
		this.result = fixtureResult;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public boolean isSet() {
		return isSet;
	}

	public void setSet(boolean isSet) {
		this.isSet = isSet;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Bet bet = (Bet) o;
		if (bet.id == null || id == null) {
			return false;
		}
		return Objects.equals(id, bet.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "Bet{" + "id=" + id + ", time='" + time + "'" + '}';
	}
}

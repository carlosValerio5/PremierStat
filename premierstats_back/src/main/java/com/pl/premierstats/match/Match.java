package com.pl.premierstats.match;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDate;
import java.time.LocalTime;


/**
 * Entidad correspondiente a la tabla <code>matches</code> en el esquema <code>csvs</code>.<br/>
 *
 * JPA mapea automáticamente esta clase a la tabla <code>matches</code> en la base de datos.<br/>
 * Esta clase contiene toda la información acerca de partidos.<br/>
 * La columna match_id corresponde a el atributo id. Esta columna representa la llave primaria de esta tabla.
 */
@Entity
@Table(name="matches", schema="csvs")
public class Match {

    @Id
    @Column(name="match_id", unique = true) //Should add sequence so it is possible to add more matches
    private Integer id;
    private Integer wk;
    private String day;
    private LocalDate date;
    @Column(name="time_match")
    private LocalTime time;
    private String home;
    private Double xg_home;
    private String score;
    private Double xg_away;
    private String away;
    private Long attendance;
    private String venue;
    private String referee;
    private Character result; //A for away won, H for home won and T for tie

    public Match() {
    }

    public Match(Integer match_id, Integer wk, String day, LocalDate date, LocalTime time, String home, Double xg_home, String score, Double xg_away, String away, Long attendance, String venue, String referee, Character result) {
        this.id = match_id;
        this.wk = wk;
        this.day = day;
        this.date = date;
        this.time = time;
        this.home = home;
        this.xg_home = xg_home;
        this.score = score;
        this.xg_away = xg_away;
        this.away = away;
        this.attendance = attendance;
        this.venue = venue;
        this.referee = referee;
        this.result = result;
    }

    public Integer getMatch_id() {
        return id;
    }

    public void setMatch_id(Integer match_id) {
        this.id = match_id;
    }

    public Integer getWk() {
        return wk;
    }

    public void setWk(Integer wk) {
        this.wk = wk;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public Double getXg_home() {
        return xg_home;
    }

    public void setXg_home(Double xg_home) {
        this.xg_home = xg_home;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Double getXg_away() {
        return xg_away;
    }

    public void setXg_away(Double xg_away) {
        this.xg_away = xg_away;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public Long getAttendance() {
        return attendance;
    }

    public void setAttendance(Long attendance) {
        this.attendance = attendance;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getReferee() {
        return referee;
    }

    public void setReferee(String referee) {
        this.referee = referee;
    }

    public Character getResult() {
        return result;
    }

    public void setResult(Character result) {
        this.result = result;
    }
}

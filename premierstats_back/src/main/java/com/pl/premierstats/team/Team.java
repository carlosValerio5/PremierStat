package com.pl.premierstats.team;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Teams class
 * each team has the same columns as a player but it'll be the total
 * of all the players stats corresponding to the team they belong to
 *
 * Columns like age will be an average for the team
 */

@Entity
@Table(name="teams_data", schema="csvs")
public class Team {

    @Id
    @Column(name="name", unique =true)
    private String name;
    private Float age;
    private Integer mp;
    private Integer starts;
    private Float min;
    private Float ninetys;
    private Integer gls;
    private Integer ast;
    private Integer pk;
    private Integer crdy;
    private Integer crdr;
    private Float xg;
    private Float npxg;
    private Float xag;
    private Long prgc;
    private Long prgp;

    public Team() {
    }

    public Team(String name) {
        this.name = name;
    }

    public Team(String name, Float age, Integer gls, Integer ast){
        this.name = name;
        this.age = age;
        this.gls = gls;
        this.ast = ast;
    }

    public Team(String name, Float age, Integer mp, Integer starts, Float min, Float ninetys, Integer gls, Integer ast,
                Integer pk, Integer crdy, Integer crdr, Float xg, Float npxg, Float xag, Long prgc, Long prgp) {
        this.name = name;
        this.age = age;
        this.mp = mp;
        this.starts = starts;
        this.min = min;
        this.ninetys = ninetys;
        this.gls = gls;
        this.ast = ast;
        this.pk = pk;
        this.crdy = crdy;
        this.crdr = crdr;
        this.xg = xg;
        this.npxg = npxg;
        this.xag = xag;
        this.prgc = prgc;
        this.prgp = prgp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getAge() {
        return age;
    }

    public void setAge(Float age) {
        this.age = age;
    }

    public Integer getMp() {
        return mp;
    }

    public void setMp(Integer mp) {
        this.mp = mp;
    }

    public Integer getStarts() {
        return starts;
    }

    public void setStarts(Integer starts) {
        this.starts = starts;
    }

    public Float getMin() {
        return min;
    }

    public void setMin(Float min) {
        this.min = min;
    }

    public Float getNinetys() {
        return ninetys;
    }

    public void setNinetys(Float ninetys) {
        this.ninetys = ninetys;
    }

    public Integer getGls() {
        return gls;
    }

    public void setGls(Integer gls) {
        this.gls = gls;
    }

    public Integer getAst() {
        return ast;
    }

    public void setAst(Integer ast) {
        this.ast = ast;
    }

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public Integer getCrdy() {
        return crdy;
    }

    public void setCrdy(Integer crdy) {
        this.crdy = crdy;
    }

    public Integer getCrdr() {
        return crdr;
    }

    public void setCrdr(Integer crdr) {
        this.crdr = crdr;
    }

    public Float getXg() {
        return xg;
    }

    public void setXg(Float xg) {
        this.xg = xg;
    }

    public Float getNpxg() {
        return npxg;
    }

    public void setNpxg(Float npxg) {
        this.npxg = npxg;
    }

    public Float getXag() {
        return xag;
    }

    public void setXag(Float xag) {
        this.xag = xag;
    }

    public Long getPrgc() {
        return prgc;
    }

    public void setPrgc(Long prgc) {
        this.prgc = prgc;
    }

    public Long getPrgp() {
        return prgp;
    }

    public void setPrgp(Long prgp) {
        this.prgp = prgp;
    }
}

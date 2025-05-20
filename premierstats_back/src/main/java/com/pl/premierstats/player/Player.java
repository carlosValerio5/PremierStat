package com.pl.premierstats.player;

import jakarta.persistence.*;

import java.util.Comparator;

/**
 * Clase que corresponde a la tabla <code>players_data</code> en la base de datos.
 * <br/>
 * Contiene toda la información acerca de jugadores.
 * Cuenta con un campo <code>playMakerScore</code> que no se encuentra en la base de datos y se identifica con la
 * anotación <code>Transient</code> para que JPA no lo tome en cuenta al mapear los campos de la clase.
 */
@Entity
@Table(name="players_data", schema = "csvs")
public class Player {

    @Id
    @Column(name="name", unique =true)
    private String name;
    private String nation;
    private String pos;
    private Integer age;
    private Integer MP;
    private Integer starts;
    private Double min;
    private Double ninetys;
    private Integer gls;
    private Integer ast;
    private Integer pk;
    private Integer crdy;
    private Integer crdr;
    private Double xg;
    private Double npxg;
    private Double xag;
    private Integer prgc;
    private Integer prgp;
    private String team;
    private Double zProgressivePasses;
    private Double zAssists;
    private Double zXag;

    @Column(name="z_goals")
    private Double zGoals;

    @Column(name="z_xg")
    private Double zXg;

    //This field is not present in the database
    @Transient
    private Double playMakerScore;

    //Constructors


    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }


    public Player(String name, String nation, String pos, Integer age, String team) {
        this.name = name;
        this.nation = nation;
        this.pos = pos;
        this.age = age;
        this.team = team;
    }

    public Player(Integer age, Integer ast, Integer crdr, Integer crdy, Integer gls, Double min, Integer MP, String name,
                  String nation, Double ninetys, Double npxg, Integer pk, String pos, Integer prgc, Integer prgp,
                  Integer starts, String team, Double xag, Double xg, Double zGoals, Double zXg) {
        this.age = age;
        this.ast = ast;
        this.crdr = crdr;
        this.crdy = crdy;
        this.gls = gls;
        this.min = min;
        this.MP = MP;
        this.name = name;
        this.nation = nation;
        this.ninetys = ninetys;
        this.npxg = npxg;
        this.pk = pk;
        this.pos = pos;
        this.prgc = prgc;
        this.prgp = prgp;
        this.starts = starts;
        this.team = team;
        this.xag = xag;
        this.xg = xg;
        this.zGoals = zGoals;
        this.zXg = zXg;
    }

    //Getters and Setters


    public Double getzGoals() {
        return zGoals;
    }

    public void setzGoals(Double zGoals) {
        this.zGoals = zGoals;
    }

    public Double getzXg() {
        return zXg;
    }

    public void setzXg(Double zXg) {
        this.zXg = zXg;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAst() {
        return ast;
    }

    public void setAst(Integer ast) {
        this.ast = ast;
    }

    public Integer getCrdr() {
        return crdr;
    }

    public void setCrdr(Integer crdr) {
        this.crdr = crdr;
    }

    public Integer getCrdy() {
        return crdy;
    }

    public void setCrdy(Integer crdy) {
        this.crdy = crdy;
    }

    public Integer getGls() {
        return gls;
    }

    public void setGls(Integer gls) {
        this.gls = gls;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Integer getMP() {
        return MP;
    }

    public void setMP(Integer MP) {
        this.MP = MP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Double getNinetys() {
        return ninetys;
    }

    public void setNinetys(Double ninetys) {
        this.ninetys = ninetys;
    }

    public Double getNpxg() {
        return npxg;
    }

    public void setNpxg(Double npxg) {
        this.npxg = npxg;
    }

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public Integer getPrgc() {
        return prgc;
    }

    public void setPrgc(Integer prgc) {
        this.prgc = prgc;
    }

    public Integer getPrgp() {
        return prgp;
    }

    public void setPrgp(Integer prgp) {
        this.prgp = prgp;
    }

    public Integer getStarts() {
        return starts;
    }

    public void setStarts(Integer starts) {
        this.starts = starts;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Double getXag() {
        return xag;
    }

    public void setXag(Double xag) {
        this.xag = xag;
    }

    public Double getXg() {
        return xg;
    }

    public void setXg(Double xg) {
        this.xg = xg;
    }

    public Double getzProgressivePasses() {
        return zProgressivePasses;
    }

    public void setzProgressivePasses(Double zProgressivePasses) {
        this.zProgressivePasses = zProgressivePasses;
    }

    public Double getzAssists() {
        return zAssists;
    }

    public void setzAssists(Double zAssists) {
        this.zAssists = zAssists;
    }

    public Double getzXag() {
        return zXag;
    }

    public void setzXag(Double zXag) {
        this.zXag = zXag;
    }

    public Double getPlayMakerScore() {
        return playMakerScore;
    }

    public void setPlayMakerScore(Double playMakerScore) {
        this.playMakerScore = playMakerScore;
    }
}

/**
 * Calse que sirve para comparar dos jugadores basado en su nota
 * de playmaker
 */
class PlayerPlayMakerComparator implements Comparator<Player> {
    @Override
    public int compare(Player o1, Player o2) {
        return o1.getPlayMakerScore().compareTo(o2.getPlayMakerScore());
    }
}
package com.pl.premierstats.player;

/**
 * Data Transfer Object for the comparison endpoint
 *
 * This design pattern is useful to show different data in the same response
 * it allows giving different views to the same data
 */
public class PlayerCompareDTO {

    //Player Information
    private String playerName;
    private String teamName;
    private String nation;
    private String position;
    private Integer age;

    //Player statistics
    private Integer minutesPlayed;
    private Double ninetys;
    private Integer matchesPlayed;
    private Double xg; //expected goals
    private Double npxg; //non-penalty expected goals
    private Integer gls;
    private Integer ast;
    private Double xag; //expected assist goals

    //Stats per 90 minutes
    private Double glsPerNinety;
    private Double astPerNinety;
    private Double xagPerNinety;
    private Double xgPerNinety;
    private Double npxgPerNinety;

    //Progression
    private Integer progressiveCarries; //Progression carries
    private Integer progressivePasses; //Progression passes

    //Progression per 90 minutes
    private Double progressiveCarriesPerNinety;
    private Double progressivePassesPerNinety;

    //Efficiency stats
    private Double goalEfficiency;
    private Double assistEfficiency;

    // Individual player score to measure impact on the field
    private Double impact;


}

DROP SCHEMA IF EXISTS csvs CASCADE;
CREATE SCHEMA csvs;

CREATE TABLE csvs.players_data(
    Player TEXT,
    Nation TEXT,
    Pos TEXT,
    Age TEXT,
    MP TEXT,
    Starts TEXT,
    Min TEXT,
    ninetys TEXT,
    Gls TEXT,
    Ast TEXT,
    GplusA TEXT,
    G_nonPenalty TEXT,
    PK TEXT,
    PKatt TEXT,
    CrdY TEXT,
    CrdR TEXT,
    xG TEXT,
    npxG TEXT,
    xAG TEXT,
    npxG_plus_xAG TEXT,
    PrgC TEXT,
    PrgP TEXT,
    PrgR TEXT,
    Gls_ninety TEXT,
    Ast_ninety TEXT,
    G_A_ninety TEXT,
    G_PK_ninety TEXT,
    G_A_nonPK_ninety TEXT,
    xG_ninety TEXT,
    xAG_ninety TEXT,
    xG_xAG_ninety TEXT,
    npxG_ninety TEXT,
    npxG_xAG_ninety TEXT,
    Matches TEXT,
    Team TEXT
);

\copy csvs.players_data(player, nation, pos, age, mp, starts, min, ninetys, gls, ast, gplusa, g_nonpenalty, pk, pkatt, crdy, crdr, xg, npxg, xag, npxg_plus_xag, prgc, prgp, prgr, gls_ninety, ast_ninety, g_a_ninety, g_pk_ninety, g_a_nonpk_ninety, xg_ninety, xag_ninety, xg_xag_ninety, npxg_ninety, npxg_xag_ninety, matches, team) FROM '/home/carlitos/PremierStat/stats.csv' DELIMITER ',' HEADER CSV;

ALTER TABLE csvs.players_data
DROP COLUMN Matches;


DROP SCHEMA IF EXISTS csvs CASCADE;
CREATE SCHEMA csvs;

CREATE TABLE csvs.players_data(
    name TEXT,
    Nation TEXT,
    Pos TEXT,
    Age TEXT,
    MP TEXT,
    Starts TEXT,
    Min TEXT,
    ninetys TEXT,
    Gls TEXT,
    Ast TEXT,
    PK TEXT,
    CrdY TEXT,
    CrdR TEXT,
    xG TEXT,
    npxG TEXT,
    xAG TEXT,
    PrgC TEXT,
    PrgP TEXT,
    Team TEXT
);

CREATE TABLE csvs.teams_data(
    name TEXT,
    Age TEXT,
    MP TEXT,
    Starts TEXT,
    Min TEXT,
    ninetys TEXT,
    Gls TEXT,
    Ast TEXT,
    PK TEXT,
    CrdY TEXT,
    CrdR TEXT,
    xG TEXT,
    npxG TEXT,
    xAG TEXT,
    PrgC TEXT,
    Prgp TEXT

);

CREATE TABLE csvs.matches(
    match_id TEXT,
    wk TEXT,
    day TEXT,
    date TEXT,
    time_match TEXT,
    home TEXT,
    xg_home TEXT,
    score TEXT,
    xg_away TEXT,
    away TEXT,
    attendance TEXT,
    venue TEXT,
    referee TEXT,
    result TEXT
);

\cd :scriptdir
\cd ../DataScraping

\COPY csvs.players_data(name, Nation, Pos, Age, MP, Starts, Min, ninetys, Gls, Ast, PK, CrdY, CrdR, xG, npxG, xAG, PrgC, PrgP, Team) FROM 'players.csv' DELIMITER ',' HEADER CSV;

\COPY csvs.teams_data(name,Age,MP,Starts,Min,ninetys,Gls,Ast,PK,CrdY,CrdR,xG,npxG,xAG,PrgC,PrgP) FROM 'teams.csv' DELIMITER ',' HEADER CSV;

\COPY csvs.matches(match_id, wk, day, date, time_match, home, xg_home, score, xg_away, away, attendance, venue, referee, result) FROM 'matches_cleaned.csv' DELIMITER ',' HEADER CSV;

--altering squads column types
--testing function to alter data types
CREATE OR REPLACE FUNCTION alter_columns_to_type(
    table_name TEXT,
    columns_and_types TEXT[]
)
RETURNS VOID AS
$$
DECLARE
    column_info TEXT;
    column_name TEXT;
    new_type TEXT;
    alter_sql TEXT;

BEGIN
    --loop through all desired columns and types
    FOREACH column_info IN ARRAY columns_and_types
    LOOP
        --splitt column name and data type
        column_name := split_part(column_info, ',', 1);
        new_type := split_part(column_info, ',', 2);

        --alter sql command
        alter_sql := 'ALTER TABLE ' || table_name ||
                     ' ALTER COLUMN ' || column_name ||
                     ' SET DATA TYPE ' || new_type ||
                     ' USING (' || column_name || '::' ||new_type || ')';

        --execute sql command
        BEGIN
            EXECUTE alter_sql;
        EXCEPTION
            WHEN OTHERS THEN
                alter_sql := 'ALTER TABLE ' || table_name ||
                            ' ALTER COLUMN ' || column_name ||
                            ' SET DATA TYPE ' || new_type ||
                            ' USING (' || column_name || '::REAL::' || new_type || ')';
                EXECUTE alter_sql;
        END;

    END LOOP;
END;
$$ LANGUAGE plpgsql;

--executing function
SELECT alter_columns_to_type(
       'csvs.teams_data',
       ARRAY[
           'age,REAL',
           'mp,INT',
           'starts,INT',
           'min,REAL',
           'ninetys,REAL',
           'gls,INT',
           'ast,INT',
           'pk,INT',
           'crdy,INT',
           'crdr,INT',
           'xg,REAL',
           'npxg,REAL',
           'xag,REAL',
           'prgc,BIGINT',
           'prgp,BIGINT'
           ]
       );

SELECT alter_columns_to_type(
            'csvs.players_data',
    ARRAY [
        'age,INT',
        'mp,INT',
        'starts,INT',
        'min,REAL',
        'ninetys,REAL',
        'gls,INT',
        'ast,INT',
        'pk,INT',
        'crdy,INT',
        'crdr,INT',
        'xg,REAL',
        'npxg,REAL',
        'xag,REAL',
        'prgc,INT',
        'prgp,INT'
        ]
    );

SELECT alter_columns_to_type(
       'csvs.matches',
       ARRAY [
           'match_id,INT',
           'wk,INT',
           'day,TEXT',
           'date,DATE',
           'time_match,TIME',
           'home,TEXT',
           'xg_home,DOUBLE PRECISION',
           'score,TEXT',
           'xg_away,DOUBLE PRECISION',
           'away,TEXT',
           'attendance,BIGINT',
           'venue,TEXT',
           'referee,TEXT',
           'result,TEXT'
           ]
       );


ALTER TABLE csvs.players_data
ADD CONSTRAINT player_pk PRIMARY KEY (name);

ALTER TABLE csvs.teams_data
ADD CONSTRAINT team_pk PRIMARY KEY (name);

ALTER TABLE csvs.matches
ADD CONSTRAINT match_pk PRIMARY KEY (match_id);


UPDATE csvs.players_data SET gls = 0 WHERE gls IS NULL;
UPDATE csvs.players_data SET prgp = 0 WHERE prgp IS NULL;

ALTER TABLE csvs.players_data
ADD COLUMN z_progressive_passes DOUBLE PRECISION,
ADD COLUMN z_assists DOUBLE PRECISION,
ADD COLUMN z_xag DOUBLE PRECISION,
ADD COLUMN z_xg DOUBLE PRECISION,
ADD COLUMN z_goals DOUBLE PRECISION;


WITH stats AS (
    SELECT
        AVG(prgp) AS mean_pp,
        STDDEV(prgp) AS std_pp,
        AVG(ast) AS mean_ast,
        STDDEV(ast) AS std_ast,
        AVG(xag) AS mean_xag,
        STDDEV(xag) AS std_xag,
        AVG(gls) AS mean_gls,
        STDDEV(gls) AS std_gls,
        AVG(xg) AS mean_xg,
        STDDEV(xg) AS std_xg
    FROM csvs.players_data
)
UPDATE csvs.players_data
SET
    z_progressive_passes = (prgp - stats.mean_pp) / NULLIF(stats.std_pp, 0),
    z_assists = (ast - stats.mean_ast) / NULLIF(stats.std_ast, 0),
    z_xag = (xag - stats.mean_xag) / NULLIF(stats.std_xag, 0),
    z_goals = (gls - stats.mean_gls) / NULLIF(stats.std_gls, 0),
    z_xg = (xg-stats.mean_xg) / NULLIF(stats.std_xg, 0)
FROM stats;

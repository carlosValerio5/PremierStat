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

\COPY csvs.players_data(name, Nation, Pos, Age, MP, Starts, Min, ninetys, Gls, Ast, PK, CrdY, CrdR, xG, npxG, xAG, PrgC, PrgP, Team) FROM '/home/carlitos/PremierStat/players.csv' DELIMITER ',' HEADER CSV;

\COPY csvs.teams_data(name,Age,MP,Starts,Min,ninetys,Gls,Ast,PK,CrdY,CrdR,xG,npxG,xAG,PrgC,PrgP) FROM '/home/carlitos/PremierStat/teams.csv' DELIMITER ',' HEADER CSV;

\COPY csvs.matches(match_id, wk, day, date, time, home, xg_home, score, xg_away, away, attendance, venue, referee, result) FROM '/home/carlitos/PremierStat/matches_cleaned.csv' DELIMITER ',' HEADER CSV;

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
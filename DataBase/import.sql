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
    Team TEXT,
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

\COPY csvs.players_data(Player, Nation, Pos, Age, MP, Starts, Min, ninetys, Gls, Ast, PK, CrdY, CrdR, xG, npxG, xAG, PrgC, PrgP, Team) FROM '/home/carlitos/PremierStat/players.csv' DELIMITER ',' HEADER CSV;

\COPY csvs.teams_data(Team,Age,MP,Starts,Min,ninetys,Gls,Ast,PK,CrdY,CrdR,xG,npxG,xAG,PrgC,PrgP) FROM '/home/carlitos/PremierStat/teams.csv' DELIMITER ',' HEADER CSV;

--note change this block into a function call
--alter block begin
ALTER TABLE csvs.players_data
ALTER COLUMN Age TYPE INT USING (Age::REAL::INT);

ALTER TABLE csvs.players_data
ALTER COLUMN MP TYPE INT USING (MP::INT);

ALTER TABLE csvs.players_data
ALTER COLUMN Starts TYPE INT USING (Starts::INT);

ALTER TABLE csvs.players_data
ALTER COLUMN Min TYPE REAL;

ALTER TABLE csvs.players_data
ALTER COLUMN ninetys TYPE REAL;

ALTER TABLE csvs.players_data
ALTER COLUMN gls TYPE INT USING (gls::REAL::INT);

ALTER TABLE csvs.players_data
ALTER COLUMN ast TYPE INT USING (ast::REAL::INT);

ALTER TABLE csvs.players_data
ALTER COLUMN pk TYPE INT USING (pk::REAL::INT);

ALTER TABLE csvs.players_data
ALTER COLUMN crdy TYPE INT USING (crdy::REAL::INT);

ALTER TABLE csvs.players_data
ALTER COLUMN crdr TYPE INT USING (crdr::REAL::INT);

ALTER TABLE csvs.players_data
ALTER COLUMN xg TYPE REAL USING (xg::REAL);

ALTER TABLE csvs.players_data
ALTER COLUMN npxg TYPE REAL USING (npxG::REAL);

ALTER TABLE csvs.players_data
ALTER COLUMN xag TYPE REAL USING (xag::REAL);

--check weather prgc includes decimal values or not
SELECT prgc FROM csvs.players_data
WHERE prgc ~ '[0-9]+\.[1-9]+';

ALTER TABLE csvs.players_data
ALTER COLUMN prgc TYPE INT USING (prgc::REAL::INT);

--check weather prgp includes decimal values or not

SELECT prgp FROM csvs.players_data
WHERE prgp ~ '[0-9]+\.[1-9]+';

ALTER TABLE csvs.players_data
ALTER COLUMN prgp TYPE INT USING (prgp::REAL::INT);

--alter block end


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
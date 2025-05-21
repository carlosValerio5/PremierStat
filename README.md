# PremierStat  ⚽
PremierStat is a web-based data analysis tool designed to provide insights and detailed statistics for the Premier League, the top professional football league.

## Visit the WebSite!!!
https://premier-stat.vercel.app/

## Purpose 🏁 
PremierStat was created to offer engaging insights into the Premier League for football fans. While its primary purpose is recreational, it can also serve as a tool for basic data analysis related to football.

## Structure
The project is divided into different directories
- <code>DataBase/</code><br />
DataBase DDL statements to setup the data base structure.

- <code>DataScraping</code><br/>
Python module used to get players data from the web. Also this directory includes some notebooks used to clean the resulting data.

- <code>premierstats_back</code>/<br/>
BackEnd logic for app's API

- <code>prueba/</code><br/>
Folder with the front end code.

## Setup 💻 
1. Clone the repository
2. Create the database <br/>
   - Create a postgresql database. <br/>
   - Grant all privileges on the database to your user. <br />
   - Run this command from the <code>DataBase</code> directory to setup the data: <br />
   <code>psql -vscriptdir=["absolute path to the dir in which the script is located"] -f import.sql</code>
3. Go to the premierstats_back folder<br/>
   - Set the spring active profile environment variable:<br/>
   <code>SPRING_PROFILES_ACTIVE</code> = dev or test. (test enables junit testing but needs the database url to be changed manually in the application-test.properties file)<br/>
   - Create an env.properties file in the <code>resources</code> folder and set the following environment variables:<br />
     - <code>DB_HOST</code> = path to your database ej. localhost<br />
     - <code>DB_PORT</code> = the port where your database is running ej. 5432 <br/>
     - <code>DB_USER</code> = the username of the owner of the database.
     - <code>DB_DATABASE</code> = the name of your database.
     - <code>DB_PASSWORD</code> = the password to your database.
   - Start the server 
4. Go to the <code>prueba</code> folder.
  - run the command <code>npm i</code> to install the dependencies.
  - Create a .env file and write the following statement:
    <code>VITE_API_URL=http://localhost:8080</code> if your spring boot server is running in a different port change it.
  - Finally start the server by running <code>npm run dev</code>
5. Enjoy!!!

## Comming Soon
**ML Predictions**<br/>
**JWT Authentication**<br/>
**WebScraping Automated job**<br/>

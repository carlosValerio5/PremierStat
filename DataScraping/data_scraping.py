from io import StringIO

from bs4 import BeautifulSoup
import requests
import pandas as pd
import time

all_teams = []
url = "https://fbref.com/en/comps/9/2023-2024/stats/2023-2024-Premier-League-Stats"

def main():
    request = requests.get(url)
    if request.status_code != 200:
        print("Status code is not 200")
        print(request.status_code)
        return
    content = request.text
    soup = BeautifulSoup(content, 'lxml')
    table = soup.find_all('table', class_="stats_table")[0] #only keeping the first instance of the table

    links = table.find_all('a') #get links which lead to teams info
    links = [l.get('href') for l in links]
    links = [l for l in links if '/squads/' in l] #collecting only the links about teams

    team_urls = [f"https://fbref.com{l}" for l in links] #turning links into urls

    for team_url in team_urls:
        team_name = team_url.split('/')[-1].replace('-Stats','') #getting team name removing irrelevant words

        request = requests.get(team_url)

        if request.status_code != 200:
            print("Status code is not 200")
            print(request.status_code)

        teams_data = request.text

        soup = BeautifulSoup(teams_data, 'lxml')#parsing contents about team
        table = soup.find_all('table', class_="stats_table")[0]  ##again, only want the first table

        #print(table.columns)

        team_stats = pd.read_html(StringIO(str(table)))[0]  # parse data into pandas dataframe

        if isinstance(team_stats.columns, pd.MultiIndex):
            team_stats.columns = team_stats.columns.droplevel()

        team_stats["Team"] = team_name
        all_teams.append(team_stats) #append team row to general data
        time.sleep(5) #making sure our request doesn't get rejected

    stat_data_frame = pd.concat(all_teams) #append data to data frame
    stat_data_frame.to_csv('stats.csv', index=False) #create csv file out of pandas dataframe

#cleaning index
def clean_csv(df):

    df.to_csv('stats.csv', index=False)
    print(df)


#Note: use the matches.ipynb notebook to correctly format the data
def pull_matches():
    url = "https://fbref.com/en/comps/9/2023-2024/schedule/2023-2024-Premier-League-Scores-and-Fixtures"
    request = requests.get(url)

    if request.status_code != 200:
        print("Request failed")
        print(request.status_code)
        return

    content = request.text
    soup = BeautifulSoup(content, 'lxml')
    table = soup.find_all('table', class_="stats_table")


    matchStats = pd.read_html(StringIO(str(table)))[0]

    #drop index
    if isinstance(matchStats.columns, pd.MultiIndex):
        matchStats.columns = matchStats.columns.droplevel()

    matchStats.to_csv('match_stats.csv', index=False)


if __name__ == "__main__":
    main()
    pull_matches()

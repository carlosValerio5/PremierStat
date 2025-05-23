import { useParams } from "react-router-dom";
import {useEffect, useState} from "react";
import SearchBar from "./SearchBar.jsx";
import SearchResults from "./SearchResults.jsx";
import TopScorers from "./TopScorers.jsx";
import TopPlayMakers from "./TopPlayMakers.jsx";


const Players = () => {

    const {name} = useParams();
    const [ players , setPlayers ] = useState([]);

    const [ results, setResults ] = useState([]);


    const API_URL = (name) ? `${import.meta.env.VITE_API_URL}/api/v1/player?name=${name}`
        : `${import.meta.env.VITE_API_URL}/api/v1/player`;

    useEffect(() => {
        const getPlayers = async () => {
            try{
                const response = await fetch(API_URL);
                const json = await response.json();
                if (Array.isArray(json)) {
                    setPlayers(json);
                }else{
                    console.error("Error fetching players. Data is not array.");
                    setPlayers([]);
                }
            }catch(e){
                console.log(e)
            }
        };

        getPlayers();
    }, []);



    return (
        <div>
            <h1 className="text-5xl font-bold">Players</h1>
            <SearchBar setResults={setResults} reuse={false} />
            <SearchResults results={results} />
            <div className="mt-20">
                <TopScorers />
                <TopPlayMakers />
            </div>
        </div>
    )
}
export default Players

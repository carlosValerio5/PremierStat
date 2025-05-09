import { useParams } from "react-router-dom";
import {useEffect, useState} from "react";
import SearchBar from "./SearchBar.jsx";
import SearchResults from "./SearchResults.jsx";


const Players = () => {

    const {name} = useParams();
    const [ players , setPlayers ] = useState([]);

    const [ results, setResults ] = useState([]);


    const API_URL = (name) ? `http://localhost:8080/api/v1/player?name=${name}`
        : "http://localhost:8080/api/v1/player";

    useEffect(() => {
        const getPlayers = async () => {
            const response = await fetch(`${API_URL}`, {})
                .then(response => response.json())
                .then(data => setPlayers(data))
                .catch(err => console.log(err));
        };

        getPlayers();
    }, []);



    return (
        <div>
            <h1>Players</h1>
            <SearchBar setResults={setResults} />
            <SearchResults results={results} />
            {players.map((player, index) => (
                <div key={index} className="player">
                    <ul>{player.name}</ul>
                </div>
            ))}
        </div>
    )
}
export default Players

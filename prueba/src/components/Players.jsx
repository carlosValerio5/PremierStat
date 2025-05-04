import { useParams } from "react-router-dom";
import {useEffect, useState} from "react";

const Players = () => {

    const API_URL = "http://localhost:8080/api/v1/player"

    const {name} = useParams();
    const [ players , setPlayers ] = useState([]);

    useEffect(() => {
        const getPlayers = async () => {
            const response = await fetch(`${API_URL}`, {})
                .then(response => response.json())
                .then(data => setPlayers(data))
                .catch(err => console.log(err));

            console.log(players);
        };

        getPlayers();
    }, []);

    if(name){
        return (
            <div>
                <h1>Players</h1>
                {players.map((player, index) => (
                    <div key={index} className="player">
                        <ul>{player.name}</ul>
                    </div>
                ))}
            </div>
        );
    }

    return (
        <div>Player</div>
    )
}
export default Players

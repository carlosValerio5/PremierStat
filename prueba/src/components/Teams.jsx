import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";

function Teams() {

    const [teams, setTeams] = useState([]);
    const { name } = useParams();

    const API_URL = (name)? `http://localhost:8080/api/v1/team?name=${name}`
        : `http://localhost:8080/api/v1/team`;

    useEffect(() => {
        const fetchTeams = async () => {
            const response = await fetch(`${API_URL}`, {})
                .then(res => res.json())
                .then(data => setTeams(data))
                .catch(err => console.log(err));
        };

        fetchTeams();
    }, [])


    return (
        <div>
            <h1>Teams</h1>
            {teams.map((team, index) => (
                <div key={index} className="team">
                    <span>{team.name}</span>
                </div>
            ))}
        </div>
    )
}

export default Teams

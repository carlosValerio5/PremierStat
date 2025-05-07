import { useParams } from "react-router-dom";
import {useState, useEffect} from "react";

const Matches = () => {

    const { id } = useParams();

    const API_URL = (id) ? `http://localhost:8080/api/v1/match?id=${id}`
        : "http://localhost:8080/api/v1/match";

    const [matches, setMatches] = useState([]);

    useEffect(() => {
        const fetchMatches = async () => {
           const response = await fetch(API_URL)
                .then((res) => res.json())
                .then((data) => setMatches(data))
                .catch((err) => console.error(err));

        }

        fetchMatches();
    }, []);


    return (
        <div>
            <h1>Matches</h1>
            {matches.map((match, index) => (
                <div key={index} className="match">
                    {match.home} {"-"} {match.away}
                </div>
            ))}
        </div>
    )
}
export default Matches

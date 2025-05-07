import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";

const PlayerDashboard = () => {
    const { name } = useParams();
    const [player, setPlayer] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchPlayer = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/v1/player?name=${name}`);
                const data = await response.json();
                setPlayer(data[0]); // assuming the API returns an array
            } catch (err) {
                setError("Failed to fetch player data.");
            } finally {
                setLoading(false);
            }
        };

        fetchPlayer();
    }, [name]);

    if (loading) return <div className="p-4">Loading...</div>;
    if (error) return <div className="p-4 text-red-500">{error}</div>;
    if (!player) return <div className="p-4">No player found.</div>;

    return (
        <div className="max-w-3xl mx-auto p-6 bg-white rounded-xl shadow-md mt-6 text-black">
            <h1 className="text-2xl font-bold mb-4">{player.name}</h1>
            <div className="grid grid-cols-2 gap-4">
                <div>
                    <p><strong>Club:</strong> {player.team}</p>
                    <p><strong>Position:</strong> {player.pos}</p>
                    <p><strong>Nationality:</strong> {player.nation}</p>
                    <p><strong>Age:</strong> {player.age}</p>
                </div>
                <div>
                    <p><strong>Appearances:</strong> {player.mp}</p>
                    <p><strong>Goals:</strong> {player.gls}</p>
                    <p><strong>Assists:</strong> {player.ast}</p>
                    <p><strong>Minutes:</strong> {player.min}</p>
                </div>
            </div>
        </div>
    );
};

export default PlayerDashboard;
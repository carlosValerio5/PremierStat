import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import PlayerBarChart from "./PlayerBarChart.jsx";
import RadialPlayer from "./RadialPlayer.jsx";

const PlayerDashboard = () => {
    const { name } = useParams();
    const [player, setPlayer] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [classification, setClassification] = useState(null);

    useEffect(() => {
        const fetchPlayer = async () => {
            try {
                const response = await fetch(`${import.meta.env.VITE_API_URL}/api/v1/player?name=${name}`);
                const data = await response.json();
                console.log(data);
                if(Array.isArray(data)) {
                    setPlayer(data[0]); // assuming the API returns an array
                }else{
                    console.error("Error fetching player dashboard. Data is not array.");
                    setPlayer(null);
                }
            } catch (err) {
                setError("Failed to fetch player data.");
            } finally {
                setLoading(false);
            }
        };

        fetchPlayer();
    }, [name]);

    useEffect(() => {
        if (player) {
            setClassification(clasifyPlayer(player));
        }
    },[player]);

    const clasifyPlayer = (player) => {
        if (!player) return null;

        const position = player.pos;
        if(position.includes("GK")){
            return "N/A";
        }
        else if(position.includes("FW")){
            if(player.ast > player.xag || player.gls > player.xg){
                return "Overperformer";
            }
            else if (player.ast < player.xag && player.gls < player.xg){
                return "Underperformer";
            }
            else{
                return "Regular Performance"
            }
        }
        else if(position.includes("MF")){

            //El Promedio de progressive carries es 24.56 para mediocampistas
            const avgPrgc = 28.335;

            //El promedio de progressive passes es 39.51 para mediocampistas
            const avgPrgp = 48.43;
            if(player.prgc > avgPrgc || player.prgp > avgPrgp){
                return "Overperformer";
            }
            else if(player.prgc < avgPrgc && player.prgp < avgPrgp){
                return "Underperformer";
            }else{
                return "Regular Performance"
            }
        }
        else if(position.includes("DF")){

            //Promedio de tarjetas rojas en la temporada para defensivos
            const avgCrdr = 0.154;

            //Promedio de tarjetas amarillas en la temporada para defensivos
            const avgCrdy = 3.367;

            if(player.crdr > avgCrdr || player.crdy > avgCrdy){
                return "Aggressive";
            }
            else if(player.crdr< avgCrdr && player.crdy < avgCrdy){
                return "Pacifist";
            }
            else {
                return "Controlled"
            }

        }
    }

    if (loading) return <div className="p-4">Loading...</div>;
    if (error) return <div className="p-4 text-red-500">{error}</div>;
    if (!player) return <div className="p-4">No player found.</div>;

    return (
        <>
            <div className="flex justify-center">
                <h1 className="bg-gradient-to-b from-orange-500 to-red-800 py-3 px-4 mx-3 rounded-md text-2xl">
                    {classification}
                </h1>
            </div>
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
            <h2 className="text-4xl mt-10 font-bold">{player.name}'s { } performance</h2>
            <div className="flex justify-start gap-4">
                <PlayerBarChart player={player} type="goals"/>
                <PlayerBarChart player={player} type="assists"/>

            </div>
            <div>
                <RadialPlayer player={player} />
            </div>
        </>

    );
};

export default PlayerDashboard;
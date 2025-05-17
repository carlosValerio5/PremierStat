import React, {useEffect, useState} from 'react'

const API_URL = 'http://localhost:8080/api/v1/compare';

const statLabels = {
    playerName: "Name",
    teamName: "Team",
    nation: "Nationality",
    position: "Position",
    age: "Age",
    matchesPlayed: "Matches Played",
    xg: "Xg",
    npxg: "Non-Penalty Xg",
    gls: "Goals",
    ast: "Assists",
    xag: "Xag",
    goalEfficiency: "Goal Efficiency",
    assistEfficiency: "Assist Efficiency",
    progressiveCarries: "Prog. Carries",
    progressivePasses: "Prog. Passes",
    glsPerNinety: "Goals/90",
    astPerNinety: "Assists/90",
    xagPerNinety: "Xag/90",
    xgPerNinety: "Xg/90",
    npxgPerNinety: "Non-Penalty Xg/90",
    progressiveCarriesPerNinety: "Prog. Carries/90",
    progressivePassesPerNinety: "Prog. Passes/90",
}

const PlayerCompare = () => {

    const [player1, setPlayer1] = useState("")
    const [player2, setPlayer2] = useState("")

    const [playerData1, setPlayerData1] = useState([])
    const [playerData2, setPlayerData2] = useState([])

    const [playerData, setPlayerData] = useState([]);

    const fetchPlayer = async (name1, name2) => {

        //Limpiamos el estado de los datos para evitar errores
        setPlayerData([]);

        try{
            if(!name1 || !name2) return null;
            const response = await fetch(`${API_URL}?player1=${name1}&player2=${name2}`);

            if(!response.ok){
                if(response.status === 404){
                    console.log("Could not find a match for Player!!");
                    return;
                }
            }

            const data = await response.json();
            if(data) setPlayerData(data);
        }catch(e){
            console.error(e);
        }
    }

    useEffect(() => {
        fetchPlayer(player1, player2);
    },[player1, player2])

    return (
        <div>
            <h1 className="flex pb-5 justify-center font-bold bg-gradient-to-b from-orange-500 to-red-800 bg-clip-text text-transparent text-5xl">
                Compare Players
            </h1>
            <div className="mt-10 gap-0 columns-1 sm:columns-2">
                <div>
                    <input placeholder="Search a player"
                           onChange={(e) => setPlayer1(e.target.value)} value={player1} />
                </div>
                <div>
                    <input placeholder="Search a player"
                           onChange={(e)=>setPlayer2(e.target.value)} value={player2} />
                </div>
            </div>
            <div>
                {playerData.length === 2 && (
                    <div className="overflow-x-auto mt-10 bg-gray">
                        <table className="min-w-full border border-gray-300 text-left">
                            <thead>
                            <tr className="backdrop-blur-lg">
                                <th className="border px-4 py-2">Stat</th>
                                <th className="border px-4 py-2">{playerData[0].playerName || "Player 1"}</th>
                                <th className="border px-4 py-2">{playerData[1].playerName || "Player 2"}</th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                Object.keys(playerData[0]).map((key)=>{

                                const player1Val = playerData[0][key];
                                const player2Val = playerData[1][key];

                                let winner;
                                let diffRatio;

                                if (typeof player1Val === "number" || player1Val === "number") {
                                    if(player1Val > player2Val) {
                                        winner = 1;
                                    }else if(player1Val < player2Val) {
                                        winner = 2;
                                    }else{
                                        winner = 0;
                                    }
                                    diffRatio = Math.abs(player1Val - player2Val)/Math.max(player1Val, player2Val);

                                }

                                const bgStyleGradient = (playerIndex) => {
                                    if(playerIndex !== winner) return {backgroundColor: "#262626"};
                                    const green = "rgba(32, 197, 94, ";
                                    const gradient = Math.min(diffRatio+0.1, 1);
                                    return { backgroundColor: `${green}${gradient}` };
                                }

                                if (key==="playerName") return null;
                                return(
                                    <tr key={key}>
                                        <th className="border px-4 py-2 font-medium">{statLabels[key]}</th>
                                        <th className="border px-4 py-2" style={bgStyleGradient(1)}>
                                            {typeof player1Val === "number" ? player1Val.toFixed(2) : player1Val}
                                        </th>
                                        <th className="border px-4 py-2" style={bgStyleGradient(2)}>
                                            {typeof player2Val === "number" ? player2Val.toFixed(2) : player2Val}
                                        </th>
                                    </tr>
                                );
                            })}
                            </tbody>
                        </table>
                    </div>
                )}
            </div>
        </div>
    )
}
export default PlayerCompare

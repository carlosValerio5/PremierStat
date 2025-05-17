import React, {useEffect, useState} from 'react'

const API_URL = 'http://localhost:8080/api/v1/compare'

const PlayerCompare = () => {

    const [player1, setPlayer1] = useState("")
    const [player2, setPlayer2] = useState("")

    const [playerData1, setPlayerData1] = useState([])
    const [playerData2, setPlayerData2] = useState([])

    const fetchPlayer = async (name1, name2) => {

        //Limpiamos el estado de los datos para evitar errores
        setPlayerData1([]);
        setPlayerData2([]);

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
            if (data[0]) setPlayerData1([data[0]]);
            if (data[1]) setPlayerData2([data[1]]);
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
                    {!playerData2 ? <p className="text-red-600">Player not Found</p>
                        : playerData2.length === 0 ? <p className="text-red-600">Player not found.</p>
                            : playerData2.map((player, index) => {
                                return (
                                    <div key={index}>
                                        <p>{player.playerName}</p>
                                    </div>
                                );
                            })

                    }
                </div>
            </div>
        </div>
    )
}
export default PlayerCompare

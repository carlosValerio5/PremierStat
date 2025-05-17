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
        <>
            <h1 className="text-5xl bg-gradient-to-b from-orange-500 to-red-800 bg-clip-text text-transparent mb-10 font-bold">Teams</h1>
            <div className="relative overflow-x-auto shadow-md rounded-lg border border-gray-400/20">
                <table className="w-full text-sm text-left rtl:text-right text-gray-400">
                    <thead className="text-xs uppercase text-gray-700 dark:text-gray-400">
                    <tr className="border-b dark:border-gray-500">
                        <th scope="col" className="px-6 py-3 bg-gray-50 dark:bg-neutral-700/20">Name</th>
                        <th scope="col" className="px-6 py-3">Age</th>
                        <th scope="col" className="px-6 py-3 bg-gray-50 dark:bg-neutral-700/20">MP</th>
                        <th scope="col" className="px-6 py-3">Starts</th>
                        <th scope="col" className="px-6 py-3 bg-gray-50 dark:bg-neutral-700/20">Min</th>
                        <th scope="col" className="px-6 py-3">90s</th>
                        <th scope="col" className="px-6 py-3 bg-gray-50 dark:bg-neutral-700/20">Goals</th>
                        <th scope="col" className="px-6 py-3">Assists</th>
                        <th scope="col" className="px-6 py-3 bg-gray-50 dark:bg-neutral-700/20">PK</th>
                        <th scope="col" className="px-6 py-3">Yellow-Cards</th>
                        <th scope="col" className="px-6 py-3 bg-gray-50 dark:bg-neutral-700/20">Red-Cards</th>
                        <th scope="col" className="px-6 py-3">XG</th>
                        <th scope="col" className="px-6 py-3 bg-gray-50 dark:bg-neutral-700/20">Non-penalty-Xg</th>
                        <th scope="col" className="px-6 py-3">Xag</th>
                        <th scope="col" className="px-6 py-3 bg-gray-50 dark:bg-neutral-700/20">Prgc</th>
                        <th scope="col" className="px-6 py-3">Prgp</th>
                    </tr>
                    </thead>
                    <tbody>
                    {teams.map((team, i) => (
                        <tr key={i} className="border-b border-gray-200 dark:border-gray-700">
                            <th scope="row" className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap bg-gray-50 dark:text-white dark:bg-neutral-700/20">
                                {team.name}
                            </th>
                            <td className="px-6 py-4">{team.age}</td>
                            <td className="px-6 py-4 bg-gray-50 dark:bg-neutral-700/20">{team.mp}</td>
                            <td className="px-6 py-4">{team.starts}</td>
                            <td className="px-6 py-4 bg-gray-50 dark:bg-neutral-700/20">{team.min}</td>
                            <td className="px-6 py-4">{team.ninetys}</td>
                            <td className="px-6 py-4 bg-gray-50 dark:bg-neutral-700/20">{team.gls}</td>
                            <td className="px-6 py-4">{team.ast}</td>
                            <td className="px-6 py-4 bg-gray-50 dark:bg-neutral-700/20">{team.pk}</td>
                            <td className="px-6 py-4">{team.crdy}</td>
                            <td className="px-6 py-4 bg-gray-50 dark:bg-neutral-700/20">{team.crdr}</td>
                            <td className="px-6 py-4">{team.xg}</td>
                            <td className="px-6 py-4 bg-gray-50 dark:bg-neutral-700/20">{team.npxg}</td>
                            <td className="px-6 py-4">{team.xag}</td>
                            <td className="px-6 py-4 bg-gray-50 dark:bg-neutral-700/20">{team.prgc}</td>
                            <td className="px-6 py-4">{team.prgp}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </>
    )
}

export default Teams

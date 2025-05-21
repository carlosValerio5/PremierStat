import {useState, useEffect} from "react";

const Matches = () => {


    const API_URL = `${import.meta.env.VITE_API_URL}/api/v1/match/all?pattern=`;

    const [matches, setMatches] = useState([]);

    const fetchMatches = async (pattern="") => {
        const response = await fetch(`${API_URL}${pattern}`)
            .then((res) => res.json())
            .then((data) => setMatches(data))
            .catch((err) => console.error(err));

    }

    useEffect(() => {
        fetchMatches();
    }, []);


    return (
        <>
            <h1 className="text-5xl bg-gradient-to-b from-orange-500 to-red-800 bg-clip-text text-transparent mb-10 font-bold">Matches</h1>
            <div className="relative overflow-x-auto shadow-md rounded-lg border border-gray-400/20">
                <div className="pb-4 bg-white dark:bg-neutral-700/10">
                    <label htmlFor="table-search" className="sr-only">Search</label>
                    <div className="relative mt-1">
                        <div
                            className="absolute inset-y-0 rtl:inset-r-0 start-0 flex items-center ps-3 pointer-events-none">
                            <svg className="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true"
                                 xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round"
                                      strokeWidth="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
                            </svg>
                        </div>
                        <input type="text" id="table-search"
                               className="block pt-2 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg w-80 bg-gray-50 focus:ring-blue-500 focus:border-blue-500 dark:bg-neutral-700 dark:border-gray-400/20 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                               placeholder="Search for items"
                        onChange={(event) => fetchMatches(event.target.value)}/>
                    </div>
                </div>
                <table className="w-full text-sm text-left rtl:text-right text-gray-400">
                    <thead className="text-xs uppercase text-gray-700 dark:text-gray-400">
                    <tr className="border-b dark:border-gray-500">
                        <th scope="col" className="px-6 py-3 bg-gray-50 dark:bg-neutral-700/20">Week</th>
                        <th scope="col" className="px-6 py-3">Day</th>
                        <th scope="col" className="px-6 py-3 bg-gray-50 dark:bg-neutral-700/20">Date</th>
                        <th scope="col" className="px-6 py-3">Home</th>
                        <th scope="col" className="px-6 py-3 bg-gray-50 dark:bg-neutral-700/20">Xg Home</th>
                        <th scope="col" className="px-6 py-3">Score</th>
                        <th scope="col" className="px-6 py-3 bg-gray-50 dark:bg-neutral-700/20">Xg Away</th>
                        <th scope="col" className="px-6 py-3">Away</th>
                        <th scope="col" className="px-6 py-3 bg-gray-50 dark:bg-neutral-700/20">Attendance</th>
                        <th scope="col" className="px-6 py-3">Venue</th>
                        <th scope="col" className="px-6 py-3 bg-gray-50 dark:bg-neutral-700/20">Referee</th>
                        <th scope="col" className="px-6 py-3">Result</th>
                    </tr>
                    </thead>
                    <tbody>
                    {matches.map((match, i) => (

                        <tr key={i} className="border-b border-gray-200 dark:border-gray-700">
                            <th scope="row"
                                className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap bg-gray-50 dark:text-white dark:bg-neutral-700/20">
                                {match.wk}
                            </th>
                            <td className="px-6 py-4">{match.day}</td>
                            <td className="px-6 py-4">{match.date}</td>
                            <td className={`px-6 py-4 ${
                                match.result === "H" ? "bg-green-700/60 text-white"
                                    : match.result === "A" ? "bg-red-700/60 text-white"
                                        : "bg-neutral-700/60"
                            }`}>{match.home}</td>
                            <td className="px-6 py-4 bg-gray-50 dark:bg-neutral-700/20">{match.xg_home}</td>
                            <td className="px-6 py-4">{match.score}</td>
                            <td className="px-6 py-4 bg-gray-50 dark:bg-neutral-700/20">{match.xg_away}</td>
                            <td className={`px-6 py-4 ${
                                match.result === "A" ? "bg-green-700/60 text-white"
                                    : match.result === "H" ? "bg-red-700/60 text-white"
                                        : "bg-neutral-700/60"
                            }`}>{match.away}</td>
                            <td className="px-6 py-4 bg-gray-50 dark:bg-neutral-700/20">{match.attendance}</td>
                            <td className="px-6 py-4">{match.venue}</td>
                            <td className="px-6 py-4 bg-gray-50 dark:bg-neutral-700/20">{match.referee}</td>
                            <td className="px-6 py-4">{match.result}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </>
    )
}
export default Matches

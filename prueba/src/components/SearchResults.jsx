import React from "react";
import { useNavigate } from 'react-router-dom';


const SearchResults = ({results}) => {

    const navigate = useNavigate();

    const handleClick = (name) => {
        navigate(`/players/${name}`);
    }

    return (
        <div className="w-2/5 m-auto bg-neutral-600/50 flex flex-col items-center text-center
        rounded-lg mt-3.5 max-h-[300px] overflow-y-scroll">
            {
                results.map((result, index) => {
                    return (
                        <div key={index} className="py-4 h-full w-full hover:bg-neutral-500"
                             onClick={() => handleClick(result.name)}>
                            {result.name}
                        </div>
                    )
                })
            }
        </div>
    )
}
export default SearchResults

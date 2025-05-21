import {Search} from "lucide-react"
import {useState} from "react";

const SearchBar = ({setResults, reuse}) => {

    const [input, setInput] = useState("");

    const fetchData = async (value) => {
        if(value === "") {
            setResults([]);
            return;
        }
        if(!reuse){
            try {
                const response = await fetch(`${import.meta.env.VITE_API_URL}/api/v1/player?name=${value}`);
                const data = await response.json();
                if(Array.isArray(data)){
                    setResults(data);
                }
                else {
                    setResults([]);
                }
            }catch(err){
                console.log(err);
            }
        }
        else{
            setResults(value);
        }
    };

    const handleChange = (value) => {
        setInput(value);
        fetchData(value);
    };

    return (
        <div className="pt-20 w-2/5 m-auto flex flex-col items-center justify-center text-center min-w-200px">
            <div className="bg-neutral-600/50 w-full rounded-lg py-2 px-15px flex items-center justify-center">
                <Search className="mx-3 text-orange-500/90"/>
                <input placeholder="Search for players" className="bg-transparent border-0 h-full text-xl w-full
                ml-1.5 focus:outline-hidden" value={input} onChange={(e) => handleChange(e.target.value)} />
            </div>
        </div>
    )
}
export default SearchBar

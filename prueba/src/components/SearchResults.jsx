
const SearchResults = ({results}) => {
    return (
        <div className="w-2/5 m-auto bg-neutral-600/50 flex flex-col items-center text-center
        rounded-lg mt-3.5 max-h-[300px] overflow-y-scroll">
            {
                results.map((result, index) => {
                    return (
                        <div key={index} className="py-4 h-full w-full hover:bg-neutral-500"
                             onClick={(event) => alert(`${result.name}`) }>
                            {result.name}
                        </div>
                    )
                })
            }
        </div>
    )
}
export default SearchResults

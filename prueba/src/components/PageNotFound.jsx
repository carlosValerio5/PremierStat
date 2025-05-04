import { Link } from "react-router-dom";

const PageNotFound = () => {
    return (
        <div className="flex items-center justify-center mt-50">
            <h1 className="text-6xl font-bold text-center tracking-wide">404 <br/>Page Not Found</h1>
            <div className="absolute flex justify-center mt-80 items-center">
                <Link to={"/"}>
                    <h1 className={`text-6xl font-bold text-center bg-gradient-to-b from-orange-400 to-red-500 text-transparent bg-clip-text`}>Go back to home</h1>
                </Link>
            </div>
        </div>
    )
}
export default PageNotFound

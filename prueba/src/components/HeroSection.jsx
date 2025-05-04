import video1 from "../assets/video1.mp4";
import video2 from "../assets/video2.mp4";

import image1 from "../assets/OldTrafford.jpg";
import image2 from "../assets/Arsenal.jpg";


function HeroSection() {


    return (
        <div className="flex flex-col items-center mt-6 lg:mt-20">
            <h1 className="text-4xl sm:text-6xl lg:text-7xl text-center tracking-wide font-bold">
                The ultimate destination for
                <span className="bg-gradient-to-r from-orange-500 to-red-800 text-transparent bg-clip-text">
                     {" "}premier league{" "}
                </span>
                fans
            </h1>
            <p className="mt-10 text-lg text-center text-neutral-500 max-w-4xl">
                Unlock powerful insights from the world’s greatest soccer league. PremierStat delivers compelling stats and smart tools to elevate your game.
            </p>
            <div className="flex justify-center my-10">
                <a href="#" className="bg-gradient-to-r from-orange-500 to-red-800 py-3 px-4 mx-3 rounded-md">
                    Start for free
                </a>
            </div>
            <div className="flex mt-10 justify-center overflow-hidden sm:h-150">
                <img src={image1} alt="Old Trafford" className="rounded-lg w-1/2 border border-orange-700 shadow-orange-400 mx-2 my-4"/>
                <img src={image2} alt="Portrait of arsenal team logo" className="rounded-lg w-1/2 border border-orange-700 shadow-orange-400 mx-2 my-4"/>
            </div>
        </div>
    )
}

export default HeroSection

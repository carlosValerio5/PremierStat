import React from 'react'
import Navbar from "./components/Navbar.jsx";
import HeroSection from "./components/HeroSection.jsx";
import FeatureSection from "./components/FeatureSection.jsx";


function App() {
    return (
        <>
            <Navbar />
            <div className="max-w-7xl mx-auto pt-20 px-6 mb-20">
                <HeroSection />
                <FeatureSection />
            </div>
        </>
    )
}

export default App

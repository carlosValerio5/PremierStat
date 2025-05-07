import React from 'react'
import Navbar from "./Navbar.jsx";
import {Outlet} from "react-router-dom";


function Layout() {
    return (
        <>
            <Navbar />
            <div className="max-w-7xl mx-auto pt-20 px-6 mb-20">
                <Outlet />
            </div>
        </>
    )
}

export default Layout
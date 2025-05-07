import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import PageNotFound from "./components/PageNotFound.jsx";
import Players from "./components/Players.jsx";
import Teams from "./components/Teams.jsx";
import Matches from "./components/Matches.jsx";
import Prueba from "./components/PlayerDashboard.jsx";
import Navbar from "./components/Navbar.jsx";
import Layout from "./components/Layout.jsx";

const router = createBrowserRouter([
    {path: "/", element: <Layout />,
        children: [
            {index: true, element: <App />},
            {path: "/players/:name?", element: <Players />},
            {path: "/teams/:name?", element: <Teams />},
            {path: "/matches/:id?", element: <Matches />},
            {path: "/players/dashboard/:name", element: <Prueba />},
        ]
    },
    {path: "*", element: <PageNotFound />}
]);

createRoot(document.getElementById('root')).render(
  <StrictMode>
      <RouterProvider router={router} />
  </StrictMode>,
)

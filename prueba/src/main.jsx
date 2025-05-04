import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import PageNotFound from "./components/PageNotFound.jsx";
import Players from "./components/Players.jsx";

const router = createBrowserRouter([
    {path: "/", element: <App />},
    {path: "*", element: <PageNotFound />},
    {path: "/players/:name?", element: <Players />}
]);

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>,
)

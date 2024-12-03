import { createBrowserRouter } from "react-router-dom";
import App from "../App"   
import Home from "@/Pages/Home"; 
import Users from "@/Pages/Users";
import Vendor from "@/Pages/Vendor";
import Admin from "@/Pages/Admin";
import Configure from "@/Pages/Configure";
import Customer from "@/Pages/Customer";


export const router = createBrowserRouter([
    {
        path: "/",
        element: <App/>,
        children: [
            { 
                path: "", 
                element: <Home/> 
            },
            { 
                path: "admin", 
                element: <Admin/>
            },
            { path: "customer", element: <Customer/> },
            { path: "users", element: <Users/> },
            { path: "vendor", element: <Vendor/> },
            { path:"Configure",element:<Configure/>}
        ],
    },
]);

export default router;
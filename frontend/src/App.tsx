import './App.css'
import {Routes, Route, BrowserRouter} from "react-router-dom";
import Home from "./components/Home.tsx";
import Navbar from "./components/navbar/Navbar.tsx";
import Polls from "./components/Polls.tsx";
import Register from "./components/Register.tsx";
import Login from "./components/Login.tsx";

function App() {
    return (
        <>
            <BrowserRouter>
                <Navbar />
                <Routes>
                    <Route path={"/"} element={<Home/>}/>
                    <Route path={"/polls"} element={<Polls/>}/>
                    <Route path={"/register"} element={<Register/>} />
                    <Route path={"/login"} element={<Login/>}/>
                </Routes>
            </BrowserRouter>
        </>
    )
}


export default App

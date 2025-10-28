import './App.css'
import {Routes, Route, BrowserRouter} from "react-router-dom";
import Home from "./components/home/Home.tsx";
import Navbar from "./components/navbar/Navbar.tsx";
import Polls from "./components/Polls.tsx";
import Register from "./components/Register.tsx";
import Login from "./components/Login.tsx";
import Footer from "./components/Footer.tsx";
import Container from "./components/utils/Container.tsx";

function App() {
    return (
        <>
            <BrowserRouter>
            <div className={"flex flex-col min-h-screen"}>
                <Container>
                    <Navbar />
                </Container>
                <main className="grow">
                    <Routes>
                        <Route path={"/"} element={<Home/>}/>
                        <Route path={"/polls"} element={<Polls/>}/>
                        <Route path={"/register"} element={<Register/>} />
                        <Route path={"/login"} element={<Login/>}/>
                    </Routes>
                </main>
                <Footer/>
            </div>
            </BrowserRouter>
        </>
    )
}


export default App

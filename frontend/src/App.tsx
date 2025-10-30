import './App.css'
import {Routes, Route, BrowserRouter} from "react-router-dom";
import Home from "./components/pages/home/Home.tsx";
import Navbar from "./components/common/Navbar.tsx";
import Polls from "./components/pages/Polls.tsx";
import Register from "./components/pages/Register.tsx";
import Login from "./components/pages/Login.tsx";
import Footer from "./components/common/Footer.tsx";
import Container from "./components/common/utils/Container.tsx";
import AuthProvider from "./context/AuthProvider.tsx";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import User from "./components/pages/User.tsx";

const queryClient = new QueryClient();

function App() {

    return (
        <QueryClientProvider client={queryClient}>
            <BrowserRouter>
                <AuthProvider>
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
                                <Route path={"/users"} element={<User/>}/>
                            </Routes>
                        </main>
                        <Footer/>
                    </div>
                </AuthProvider>
            </BrowserRouter>
        </QueryClientProvider>
    )
}

export default App

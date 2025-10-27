import {NavLink} from "react-router-dom";

export default function Navbar() {
    return <nav aria-label="main navigation" className={"sticky py-2 flex items-center justify-around"}>
        <div>
            <NavLink to={"/"} className={"logo"}>
                FeedApp
            </NavLink>
        </div>
        <div className="flex gap-5 items-center">
            <NavLink to={"/polls"} className={({isActive}:{isActive:boolean}   ) =>
                isActive ? "font-semibold" : "hover:font-semibold"}>
                Polls
            </NavLink>
            <NavLink to={"/register"} className={"py-2 px-6 rounded-sm border hover:bg-black hover:text-white"}>
                Register
            </NavLink>
            <NavLink to={"/login"} className={"py-2 px-6 rounded-sm border bg-black text-white hover:bg-white hover:text-black hover:border "}>
                Login
            </NavLink>
        </div>
    </nav>
}
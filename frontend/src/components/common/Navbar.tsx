import {NavLink} from "react-router-dom";
import {useContext, useState} from "react";
import {UserTokenContext} from "../../context/Context.tsx";

export default function Navbar() {
    const {userToken,setUserToken} = useContext(UserTokenContext);
    const [openUserMenu, setOpenUserMenu] = useState(false);

    return <nav aria-label="main navigation" className={"sticky top-0 pt-3 flex items-center justify-between"}>
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
            {userToken.token.trim().length == 0 ?
                <>
                    <NavLink to={"/register"} className={"py-2 px-6 rounded-sm border hover:bg-black hover:text-white"}>
                        Register
                    </NavLink>
                    <NavLink to={"/login"} className={"py-2 px-6 rounded-sm border bg-black text-white hover:bg-white hover:text-black hover:border "}>
                        Login
                    </NavLink>
                </>
            :
                <div className="relative flex justify-center items-center">
                    <button className={"relative"} aria-label={"open usermenu"} onClick={() => setOpenUserMenu(prev => !prev)}>
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" className="size-6">
                            <path fillRule="evenodd" d="M18.685 19.097A9.723 9.723 0 0 0 21.75 12c0-5.385-4.365-9.75-9.75-9.75S2.25 6.615 2.25 12a9.723 9.723 0 0 0 3.065 7.097A9.716 9.716 0 0 0 12 21.75a9.716 9.716 0 0 0 6.685-2.653Zm-12.54-1.285A7.486 7.486 0 0 1 12 15a7.486 7.486 0 0 1 5.855 2.812A8.224 8.224 0 0 1 12 20.25a8.224 8.224 0 0 1-5.855-2.438ZM15.75 9a3.75 3.75 0 1 1-7.5 0 3.75 3.75 0 0 1 7.5 0Z" clipRule="evenodd" />
                        </svg>
                    </button>
                    {openUserMenu && <ul className={"usermenu shadow-sm"}>
                        <li>
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" className="size-6">
                                <path d="M11.47 3.841a.75.75 0 0 1 1.06 0l8.69 8.69a.75.75 0 1 0 1.06-1.061l-8.689-8.69a2.25 2.25 0 0 0-3.182 0l-8.69 8.69a.75.75 0 1 0 1.061 1.06l8.69-8.689Z" />
                                <path d="m12 5.432 8.159 8.159c.03.03.06.058.091.086v6.198c0 1.035-.84 1.875-1.875 1.875H15a.75.75 0 0 1-.75-.75v-4.5a.75.75 0 0 0-.75-.75h-3a.75.75 0 0 0-.75.75V21a.75.75 0 0 1-.75.75H5.625a1.875 1.875 0 0 1-1.875-1.875v-6.198a2.29 2.29 0 0 0 .091-.086L12 5.432Z" />
                            </svg>
                            <NavLink to={"/users"} className={"text-sm"}>
                                Profile
                            </NavLink>
                        </li>
                        <li className={"hover:text-red-500"} onClick={()=> setUserToken({token:""})}>
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" className="size-6 logout-btn">
                                <path fillRule="evenodd" d="M16.5 3.75a1.5 1.5 0 0 1 1.5 1.5v13.5a1.5 1.5 0 0 1-1.5 1.5h-6a1.5 1.5 0 0 1-1.5-1.5V15a.75.75 0 0 0-1.5 0v3.75a3 3 0 0 0 3 3h6a3 3 0 0 0 3-3V5.25a3 3 0 0 0-3-3h-6a3 3 0 0 0-3 3V9A.75.75 0 1 0 9 9V5.25a1.5 1.5 0 0 1 1.5-1.5h6ZM5.78 8.47a.75.75 0 0 0-1.06 0l-3 3a.75.75 0 0 0 0 1.06l3 3a.75.75 0 0 0 1.06-1.06l-1.72-1.72H15a.75.75 0 0 0 0-1.5H4.06l1.72-1.72a.75.75 0 0 0 0-1.06Z" clipRule="evenodd" />
                            </svg>
                            <button className={"text-sm"}>Log out</button>
                        </li>
                    </ul>}
                </div>
            }
        </div>
    </nav>
}
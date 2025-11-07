import {type ReactNode, useState} from "react";
import {UserDataContext} from "./Context.tsx";
import type {UserContext} from "../interfaces/Interfaces.ts";

export default function AuthProvider({children}: {children: ReactNode}) {
    const [userData, setUserData] = useState<UserContext>({
        token: "",
        id: ""
    });

    return <UserDataContext value={{userData, setUserData}}>
            {children}
        </UserDataContext>
}
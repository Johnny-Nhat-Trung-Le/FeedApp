import {type ReactNode, useState} from "react";
import {UserTokenContext} from "./Context.tsx";
import type {TokenContext} from "../interfaces/Interfaces.ts";

export default function AuthProvider({children}: {children: ReactNode}) {
    const [userToken, setUserToken] = useState<TokenContext>({
        token: "",
    });

    return <UserTokenContext value={{userToken, setUserToken}}>
            {children}
        </UserTokenContext>
}
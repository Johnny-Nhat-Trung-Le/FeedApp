import {createContext} from "react";
import type { UserTokenInterface} from "../components/interfaces/Interfaces.ts";

export const UserTokenContext = createContext<UserTokenInterface>({
    userToken: {
        token: "",
    },
    setUserToken: () => {},
})
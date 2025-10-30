import {createContext} from "react";
import type { UserTokenInterface} from "../interfaces/Interfaces.ts";

export const UserTokenContext = createContext<UserTokenInterface>({
    userToken: {
        token: "",
    },
    setUserToken: () => {},
})
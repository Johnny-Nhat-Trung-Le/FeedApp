import {createContext} from "react";
import type { UserDataInterface} from "../interfaces/Interfaces.ts";

export const UserDataContext = createContext<UserDataInterface>({
    userData: {
        token: "",
        username: "",
        id: "",
    },
    setUserData: () => {},
})
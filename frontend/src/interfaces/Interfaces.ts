import type {Dispatch, SetStateAction} from "react";

export interface UserContext {
    token: string;
    id: string;
}

export interface UserDataInterface {
    userData: UserContext;
    setUserData: Dispatch<SetStateAction<UserContext>>
}
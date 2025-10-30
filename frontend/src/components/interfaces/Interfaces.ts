import type {Dispatch, SetStateAction} from "react";

export interface TokenContext {
    token: string;
}

export interface UserTokenInterface {
    userToken: TokenContext;
    setUserToken: Dispatch<SetStateAction<TokenContext>>
}
import {useContext} from "react";
import {UserTokenContext} from "../../context/Context.tsx";

export default function User(){
    const {userToken} = useContext(UserTokenContext);

    console.log(userToken);

    return (<p>I am a user</p>)
}
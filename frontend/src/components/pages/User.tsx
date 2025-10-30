import {useContext} from "react";
import {UserTokenContext} from "../../context/Context.tsx";
import Container from "../common/utils/Container.tsx";

export default function User(){
    const {userToken} = useContext(UserTokenContext);

    console.log(userToken);

    return <Container>
        <p>I am a user</p>
    </Container>
}
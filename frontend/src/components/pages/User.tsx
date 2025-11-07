import {useContext} from "react";
import {UserDataContext} from "../../context/Context.tsx";
import Container from "../common/utils/Container.tsx";

export default function User(){
    const {userData} = useContext(UserDataContext);

    console.log(userData);

    return <Container>
        <p>I am a user</p>
    </Container>
}
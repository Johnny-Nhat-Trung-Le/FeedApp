import {useContext, useState} from "react";
import {UserDataContext} from "../../context/Context.tsx";
import Container from "../common/utils/Container.tsx";
import CreatePoll from "./polls/CreatePoll.tsx";

export default function User(){
    const {userData} = useContext(UserDataContext);
    const [open,setOpen] = useState(false);

    console.log(userData);

    return <Container>
        {open && <CreatePoll onClose = {() => setOpen(false)} /> }
        <button onClick = {() => setOpen(true)}> Create Poll </button>
    </Container>
}
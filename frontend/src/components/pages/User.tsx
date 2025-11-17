import {useContext, useMemo, useState} from "react";
import {UserDataContext} from "../../context/Context.tsx";
import Container from "../common/utils/Container.tsx";
import CreatePoll from "./polls/CreatePoll.tsx";
import {useQuery} from "@tanstack/react-query";
import axios from "axios";
import type {PollType} from "../../interfaces/Types.tsx";
import Poll from "./polls/Poll.tsx";

export default function User(){
    const {userData} = useContext(UserDataContext);
    const [open,setOpen] = useState(false);

    const {data : pollResults} = useQuery({
        queryKey: [`userData`],
        queryFn: () => {
            return axios.get(`http://localhost:8080/api/v1/public/polls`);
        }
    })

    const userPolls = useMemo(() => {
        if (Array.isArray(pollResults?.data)) {
            return [...pollResults.data].filter(poll => poll.creator === userData.id);
        }
        return [];
    }, [pollResults]);

    return <Container>
        <section className={"border-b pb-5 pt-10"}>
            <ul className={"flex items-center gap-4"}>
                <li>All</li>
                <li>
                    <button onClick = {() => setOpen(true)} className={"py-2 px-4 rounded-sm self-start bg-primary text-white border hover:bg-white hover:text-primary hover:border-primary"}> Create Poll </button>
                </li>
            </ul>
        </section>
        {open && <CreatePoll onClose = {() => setOpen(false)} /> }
        <section className={"py-10 mb-10"}>
            <ul className={"flex flex-wrap gap-4"}>
                {userPolls?.map((poll: PollType) => (
                    <li key={poll.id}>
                        <Poll poll={poll} />
                    </li>
                ))}
            </ul>
        </section>
    </Container>
}
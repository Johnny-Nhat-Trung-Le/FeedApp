import type {PollType} from "../../../interfaces/Types.tsx";
import Poll from "./Poll.tsx";
import {useQuery} from "@tanstack/react-query";
import axios from "axios";
import {useMemo} from "react";

export default function LatestPolls() {
    const MAX_POLLS = 3;

    const {data : pollResults} = useQuery({
        queryKey: [`polls`],
        queryFn: () => {
            return axios.get(`http://localhost:8080/api/v1/public/polls`);
        }
    })

    const latestPolls = useMemo(() => {
        if (Array.isArray(pollResults?.data)){
            return [...pollResults.data].sort((a, b) => new Date(b.publishedAt).getTime() - new Date(a.publishedAt).getTime());
        } else {
            return []
        }
    }, [pollResults]);

    return <ul className={"cards-container"}>
            {latestPolls.slice(0, MAX_POLLS).map((poll: PollType) => (
                <li key={poll.id} className={"flex-1"}>
                    <Poll poll={poll} />
                </li>
            ))}
    </ul>
}
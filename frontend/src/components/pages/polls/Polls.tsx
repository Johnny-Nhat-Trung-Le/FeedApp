import Container from "../../common/utils/Container.tsx";
import {useQuery} from "@tanstack/react-query";
import axios from "axios";
import type {PollType} from "../../../interfaces/Types.tsx";
import Poll from "./Poll.tsx";

export default function Polls() {
    const {data : pollResults} = useQuery({
        queryKey: [`polls`],
        queryFn: () => {
           return axios.get(`http://localhost:8080/api/v1/public/polls`);
        }
    })

    return <Container>
        <ul>
            {pollResults?.data?.map((poll: PollType) => (
                <li key={poll.id}>
                    <Poll poll={poll} />
                </li>
            ))}
        </ul>
    </Container>
}
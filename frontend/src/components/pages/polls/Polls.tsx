import Container from "../../common/utils/Container.tsx";
import {useQuery} from "@tanstack/react-query";
import axios from "axios";
import type {PollType} from "../../../interfaces/Types.tsx";
import Poll from "./Poll.tsx";
import Carousel from "./Carousel.tsx";

export default function Polls() {
    const {data : pollResults} = useQuery({
        queryKey: [`polls`],
        queryFn: () => {
           return axios.get(`http://localhost:8080/api/v1/public/polls`);
        }
    })

    return <>
        <section className={"py-10 mt-10 bg-light-pink/18"}>
            <Container>
                <div className={"text-center"}>
                    <h2 className={"mb-5 font-bold inline-block border-b text-center"}>Latest polls</h2>
                </div>
                <Carousel/>
            </Container>
        </section>
        <section className={"py-10 mb-10"}>
            <Container>
                <h2 className={"mb-5 font-bold"}>All polls</h2>
                <ul className={"cards-container"}>
                    {pollResults?.data?.map((poll: PollType) => (
                        <li key={poll.id} className={"flex-1"}>
                            <Poll poll={poll} />
                        </li>
                    ))}
                </ul>
            </Container>
        </section>
    </>

}
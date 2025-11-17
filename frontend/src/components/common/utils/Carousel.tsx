import { useState, useEffect, useMemo } from "react";
import Poll from "../../pages/polls/Poll.tsx";
import Button from "./Button.tsx";
import Container from "./Container.tsx";
import type { PollType } from "../../../interfaces/Types.tsx";
import { useQuery } from "@tanstack/react-query";
import axios from "axios";

export default function Carousel() {

    const { data: pollResults} = useQuery({
        queryKey: ['polls'],
        queryFn: () => axios.get(`http://localhost:8080/api/v1/public/polls`),
    });

    const pollDatas = useMemo(() => {
        if (Array.isArray(pollResults?.data)) {
            return [...pollResults.data];
        }
        return [];
    }, [pollResults]);

    const [pollIndex, setPollIndex] = useState(0);

    //Divide the polls into chunks of size 3
    const pollSlide = useMemo(()=>{
        const polls = [];
        for(let i=0; i<pollDatas.length; i+=3){
            polls.push(pollDatas.slice(i,i+3));
            }
        return polls;
        },[pollDatas]);


    const handlePrevPoll = () => {
        setPollIndex((index) => {
                    if (index === 0) return pollSlide.length - 1;
                    return index - 1;
                });
    };

    const handleNextPoll = () => {
        setPollIndex((index) => {
                    if (index === pollSlide.length - 1) return 0;
                    return index + 1;
        });
    };


    // If no polls are in the system
    if (pollSlide.length === 0) {
        return <div></div>;
    }

    return (
        <Container>

            <ul className={"cards-container"}>
              {pollSlide[pollIndex].map((poll) => (
                <li key={poll.id} className={"flex-1"}>
                  <Poll poll={poll} />
                </li>
              ))}
            </ul>
            <button
                className="bg-primary text-white border hover:bg-white hover:text-primary hover:border-primary"
                onClick={handlePrevPoll}
            > Previous</button>
            <button
                className="bg-primary text-white border hover:bg-white hover:text-primary hover:border-primary"
                onClick={handleNextPoll}
            > Next</button>
        </Container>
    );
}


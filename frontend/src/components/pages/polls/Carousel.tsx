import { useState, useMemo } from "react";
import Poll from "./Poll.tsx";
import Container from "../../common/utils/Container.tsx";
import { useQuery } from "@tanstack/react-query";
import axios from "axios";

export default function Carousel() {
    const MAX_POLLS = 6;

    const { data: pollResults} = useQuery({
        queryKey: ['polls'],
        queryFn: () => axios.get(`http://localhost:8080/api/v1/public/polls`),
    });

    // Get the latest polls
    const pollDatas = useMemo(() => {
        if (Array.isArray(pollResults?.data)) {
            return [...pollResults.data].slice(0, MAX_POLLS).sort((a, b) => new Date(b.publishedAt).getTime() - new Date(a.publishedAt).getTime());
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
        <Container style={"my-10 flex items-center gap-5"}>
            {/* left button */ }
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="size-6" onClick={handlePrevPoll}>
                <path fillRule="evenodd" d="M7.72 12.53a.75.75 0 0 1 0-1.06l7.5-7.5a.75.75 0 1 1 1.06 1.06L9.31 12l6.97 6.97a.75.75 0 1 1-1.06 1.06l-7.5-7.5Z" clipRule="evenodd" />
            </svg>
            {/* carousel content */ }
            <ul className={"flex gap-2 w-full flex-wrap justify-center"}>
              {pollSlide[pollIndex].map((poll) => (
                <li key={poll.id}>
                  <Poll poll={poll} />
                </li>
              ))}
            </ul>
            {/* right button */ }
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="size-6" onClick={handleNextPoll}>
                <path fillRule="evenodd" d="M16.28 11.47a.75.75 0 0 1 0 1.06l-7.5 7.5a.75.75 0 0 1-1.06-1.06L14.69 12 7.72 5.03a.75.75 0 0 1 1.06-1.06l7.5 7.5Z" clipRule="evenodd" />
            </svg>
        </Container>
    );
}


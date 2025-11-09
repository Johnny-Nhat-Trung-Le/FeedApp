import type {PollOptionsType, PollType, VoteRequestType} from "../../../interfaces/Types.tsx";
import {useContext, useEffect, useState} from "react";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import axios from "axios";
import {UserDataContext} from "../../../context/Context.tsx";

export default function Poll({poll} : {poll:PollType}) {
    const [totalVotes, setTotalVotes] = useState<number>(0);
    const [votesPerOptions, setVotesPerOptions] = useState(new Map());
    const [selectedOption, setSelectedOption] = useState<string | null>(null);

    const {userData} = useContext(UserDataContext);
    const userIsAuthenticated: boolean = userData.id !== "" && userData.token !== '';

    const disabledStyle: string = "disabled:border disabled:bg-gray-400 disabled:hover:text-white";
    const normalStyle: string = "border bg-black hover:bg-white hover:text-black hover:border";

    const queryClient = useQueryClient();
    const voteOptions : PollOptionsType[] = poll.options;
    // sort the options based on presentationOrder in ascending order
    voteOptions.sort((a, b) => a.presentationOrder - b.presentationOrder);

    const voteMutation = useMutation({
        mutationFn: (voteRequest: VoteRequestType) => {
            console.log("vote request", voteRequest);
            const config = {
                headers: {
                    'Authorization': `Bearer ${userData.token}`
                }
            }
            return axios.post(`http://localhost:8080/api/v1/public/polls/${poll.id}`, voteRequest, config);
        },
        onSuccess: () => {
            queryClient.invalidateQueries({
                queryKey: ['polls'],
            })
        }
    })

    function getVotes():void{
        let totalVotes: number = 0;
        let votesMap: Map<string, number> = new Map();

        // get number of votes for each option
        voteOptions.forEach((option:PollOptionsType) => {
            votesMap.set(option.caption, option.votes.length);
            totalVotes += option.votes.length;
        });

        // save to results to be used later
        setVotesPerOptions(votesMap);
        setTotalVotes(totalVotes);
    }

    function handleVoteSubmit(){
        if (selectedOption !== null) {
            const voteRequest: VoteRequestType = {
                voteOption: {
                    id: selectedOption,
                },
                userId: userIsAuthenticated ? userData.id : "",
            }
            voteMutation.mutate(voteRequest);
        }
    }

    // everytime someone has voted we want to update the results
    useEffect(() => {
        getVotes();
    }, [poll])

    return <article className={"py-5 px-8 bg-white max-w-xs min-w-2xs flex flex-col justify-center items-center gap-5 shadow-lg rounded-sm"}>
        <h3 className={"font-bold text-center capitalize"}>{poll.question}</h3>
        <ul className={"flex flex-col gap-3 w-full max-h-33 overflow-y-auto"}>
            {voteOptions.map((option: PollOptionsType) => (
                <li key={option.id} className={`flex flex-col gap-2 border p-2 ${selectedOption == option.id ? "border-light-pink" : "border-transparent"}`} onClick={() => setSelectedOption(option.id)}>
                    <label htmlFor={`vote option progress bar for the option ${option.caption}`} className={"capitalize"}>{option.caption}</label>
                    <progress value={votesPerOptions.get(option.caption)} max={totalVotes}></progress>
                </li>
            ))}
        </ul>
        <p className={"self-start text-sm pl-2"}>{totalVotes} votes</p>
        <button disabled={selectedOption === null} onClick={() => handleVoteSubmit()} className={`py-2 px-6 rounded-sm text-sm text-white ${selectedOption === null ? disabledStyle : normalStyle}`}>Vote</button>
    </article>
}
export type UserType = {
    username: string;
    email: string;
    password: string;
}

export type LoginType = {
    username: string,
    password: string,
}

export type PollType = {
    id: string,
    question: string,
    publishedAt: string,
    creator: string,
    options: PollOptionsType[]
}

export type PollOptionsType = {
    id: string,
    caption: string,
    presentationOrder: number,
    votes: VoteType[],
}

export type PollOptionsCreateType = {
    caption: string,
    presentationOrder: number,
}

export type VoteType = {
    id: string,
    publishedAt: string,
    userId: string
}

export type VoteRequestAuthType = {
    id: string,
    voteOption: {
        id: string,
    }
    userId: string,
}

export type VoteRequestType = {
    voteOption: {
        id: string,
    }
    userId: string,
}


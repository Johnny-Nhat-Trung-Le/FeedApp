import type {Dispatch, SetStateAction} from "react";

export interface UserContext {
    token: string;
    id: string;
}

export interface UserDataInterface {
    userData: UserContext;
    setUserData: Dispatch<SetStateAction<UserContext>>
}

export interface VoteOptionType {
  caption: string;
  presentationOrder: number;
}

export interface VoteType {
  id?: string | undefined;
  pollId?: number;
  userId?: number;
  voteOption: VoteOptionType;
  publishedAt?: string;
}

export interface PollType {
  id: string;
  question: string;
  options: VoteOptionType[];
  creator: {
      id: string;
  };
  visibility: boolean;
  publishedAt: string;
  validUntil: string;
  votes: VoteType[];
}

export interface UserType {
  id: string;
  username: string;
  email: string;
  password: string;
}
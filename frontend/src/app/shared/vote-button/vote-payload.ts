import { VoteType } from './vote-type';

export class VotePayload {
    type: VoteType;
    postId: number;
}
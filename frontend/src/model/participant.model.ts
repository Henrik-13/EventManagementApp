export type Participant = {
  id: number;
  name: string;
  email: string;
};

export type CreateParticipant = Omit<Participant, 'id'>;

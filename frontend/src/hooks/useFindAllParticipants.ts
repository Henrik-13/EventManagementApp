import { useQuery } from '@tanstack/react-query';
import { Participant } from '../model/participant.model';
import { findAllParticipants } from '../api/participant.api';

export default function useFindAllParticipants(eventId: number) {
  return useQuery<Participant[]>({
    queryKey: ['participants', eventId],
    queryFn: () => findAllParticipants(eventId),
  });
}

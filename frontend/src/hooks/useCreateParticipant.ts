import { useMutation, useQueryClient } from '@tanstack/react-query';
import { createParticipant } from '../api/participant.api';
import { CreateParticipant } from '../model/participant.model';

export default function useCreateParticipant() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ eventId, data }: { eventId: number; data: CreateParticipant }) => createParticipant(eventId, data),
    onSuccess: (_, { eventId }) => {
      queryClient.invalidateQueries({ queryKey: ['participants', eventId] });
    },
  });
}

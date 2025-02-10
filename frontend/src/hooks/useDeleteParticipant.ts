import { useMutation, useQueryClient } from '@tanstack/react-query';
import { deleteParticipant } from '../api/participant.api';

export default function useDeleteParticipant() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ eventId, participantId }: { eventId: number; participantId: number }) =>
      deleteParticipant(eventId, participantId),
    onSuccess: (_, { eventId }) => {
      queryClient.invalidateQueries({ queryKey: ['participants', eventId] });
    },
  });
}

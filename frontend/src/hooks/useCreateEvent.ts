import { useMutation, useQueryClient } from '@tanstack/react-query';
import { createEvent } from '../api/event.api';

export default function useCreateEvent() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: createEvent,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['events'] });
    },
  });
}

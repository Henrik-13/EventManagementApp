import { useMutation, useQueryClient } from '@tanstack/react-query';
import { updateEvent } from '../api/event.api';
import { CreateEvent } from '../model/event.model';

export default function useUpdateEvent() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ id, data }: { id: number; data: CreateEvent }) => updateEvent(id, data),
    onSuccess: (id) => {
      queryClient.invalidateQueries({ queryKey: ['events'] });
      queryClient.invalidateQueries({ queryKey: ['event', id] });
    },
  });
}

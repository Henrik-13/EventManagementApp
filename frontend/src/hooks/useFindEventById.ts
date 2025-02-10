import { useQuery } from '@tanstack/react-query';
import { findEventById } from '../api/event.api';
import { Event } from '../model/event.model';

export default function useFindEventById(id: number) {
  return useQuery<Event>({
    queryKey: ['event', id],
    queryFn: () => findEventById(id),
    enabled: !!id,
  });
}

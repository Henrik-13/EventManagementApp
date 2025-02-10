import { useQuery } from '@tanstack/react-query';
import { Event } from '../model/event.model';
import { findAllEvents } from '../api/event.api';
import { EventFilter } from '../model/eventfilter.model';

export default function useFindAllEvents(filter: EventFilter | undefined) {
  return useQuery<Event[]>({
    queryKey: ['events', filter],
    queryFn: () => findAllEvents(filter),
  });
}

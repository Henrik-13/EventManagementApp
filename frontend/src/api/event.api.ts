import { CreateEvent, Event } from '../model/event.model';
import { EventFilter } from '../model/eventfilter.model';
import { api } from './common.api';

export async function findAllEvents(filter: EventFilter | undefined): Promise<Event[]> {
  const response = await api.get<Event[]>('/events', {
    params: filter,
  });
  return response.data;
}

export async function findEventById(id: number): Promise<Event> {
  const response = await api.get<Event>(`/events/${id}`);
  return response.data;
}

export async function findEventsByTitle(title: string): Promise<Event[]> {
  const response = await api.get<Event[]>('/events', {
    params: { title },
  });
  return response.data;
}

export async function createEvent(data: CreateEvent): Promise<Event> {
  const response = await api.post<Event>('/events', data);
  return response.data;
}

export async function deleteEvent(id: number): Promise<void> {
  await api.delete<Event>(`/events/${id}`);
}

export async function updateEvent(id: number, data: CreateEvent): Promise<Event> {
  const response = await api.put<Event>(`/events/${id}`, data);
  return response.data;
}

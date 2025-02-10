import { CreateParticipant, Participant } from '../model/participant.model';
import { api } from './common.api';

export async function findAllParticipants(eventId: number): Promise<Participant[]> {
  const response = await api.get<Participant[]>(`/events/${eventId}/participants`);
  return response.data;
}

export async function createParticipant(eventId: number, data: CreateParticipant): Promise<Participant> {
  const response = await api.post<Participant>(`/events/${eventId}/participants`, data);
  return response.data;
}

export async function deleteParticipant(eventId: number, participantId: number): Promise<void> {
  await api.delete(`/events/${eventId}/participants/${participantId}`);
}

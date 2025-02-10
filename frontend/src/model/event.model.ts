export type Event = {
  id: number;
  title: string;
  location: string;
  date: string;
  time: string;
  description: string;
  online: boolean;
};

export type CreateEvent = Omit<Event, 'id'>;

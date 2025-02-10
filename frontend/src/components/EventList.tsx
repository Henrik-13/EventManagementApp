import { Box, CircularProgress, Grid2, Typography } from '@mui/material';
import { useTranslation } from 'react-i18next';
import { useState } from 'react';
import { EventFilter } from '../model/eventfilter.model';
import EventListEntry from './EventListEntry';
import useFindAllEvents from '../hooks/useFindAllEvents';
import EventFilterForm from './EventFilterForm';
import { Event } from '../model/event.model';

export default function EventList() {
  const { t } = useTranslation();
  const [filter, setFilter] = useState<EventFilter | undefined>(undefined);
  const { data, isLoading, isError, error } = useFindAllEvents(filter);

  const handleFilter = (newFilter: EventFilter) => {
    setFilter(newFilter);
  };

  if (isLoading) {
    return <CircularProgress />;
  }

  if (isError) {
    return (
      <Typography color="error">
        {t('common.error')} {error instanceof Error ? error.message : t('common.couldntLoadEvents')}
      </Typography>
    );
  }

  return (
    <Box sx={{ padding: 4 }}>
      <Typography variant="h4" gutterBottom>
        {t('common.events')}
      </Typography>
      <EventFilterForm onFilter={handleFilter} />
      <Grid2 container spacing={3}>
        {data?.map((event: Event) => <EventListEntry key={event.id} event={event} />)}
      </Grid2>
    </Box>
  );
}

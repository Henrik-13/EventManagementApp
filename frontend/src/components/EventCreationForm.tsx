import { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { Box, Button, Checkbox, CircularProgress, FormControlLabel, Stack, TextField, Typography } from '@mui/material';
import { useTranslation } from 'react-i18next';
import { findEventById } from '../api/event.api';
import useCreateEvent from '../hooks/useCreateEvent';
import useUpdateEvent from '../hooks/useUpdateEvent';

export default function EventCreationForm() {
  const { t } = useTranslation();
  const [title, setTitle] = useState<string>('');
  const [location, setLocation] = useState<string>('');
  const [date, setDate] = useState<string>('');
  const [time, setTime] = useState<string>('');
  const [description, setDescription] = useState<string>('');
  const [online, setOnline] = useState<boolean>(false);
  const [eventId, setEventId] = useState<number | null>();
  const [loading, setLoading] = useState<boolean>(false);

  const navigate = useNavigate();
  const locationQuery = useLocation();
  const { mutate: createEvent, isPending: isCreating, isError: isCreateError, error: createError } = useCreateEvent();
  const { mutate: updateEvent, isPending: isUpdating, isError: isUpdateError, error: updateError } = useUpdateEvent();

  useEffect(() => {
    const params = new URLSearchParams(locationQuery.search);
    const id = params.get('update');

    if (id) {
      (async () => {
        setLoading(true);
        const event = await findEventById(parseInt(id, 10));
        if (event) {
          setTitle(event.title);
          setLocation(event.location);
          setDate(event.date);
          setTime(event.time);
          setDescription(event.description);
          setOnline(event.online);
          setEventId(event.id);
        }
        setLoading(false);
      })();
    }
  }, [locationQuery.search]);

  const handleSubmit = () => {
    const formattedTime = time.substring(0, 5);
    const eventData = { title, location, date, time: formattedTime, description, online };
    if (title === '' || location === '' || date === '' || time === '' || description === '') {
      return;
    }
    if (eventId) {
      updateEvent({ id: eventId, data: eventData });
      navigate(`/event_details/${eventId}`);
    } else {
      createEvent(eventData);
      navigate('/');
    }
  };

  if (loading) {
    return <CircularProgress />;
  }

  return (
    <Box
      component="form"
      sx={{
        maxWidth: 600,
        margin: 'auto',
        mt: 2,
        mb: 2,
        backgroundColor: (theme) => (theme.palette.mode === 'dark' ? '#000000' : theme.palette.background.default),
        borderRadius: 2,
        padding: 2,
      }}
      onSubmit={(e) => e.preventDefault()}
    >
      <Typography variant="h5" gutterBottom>
        {eventId ? t('event.updateEvent') : t('event.createEvent')}
      </Typography>
      <Stack spacing={3}>
        <TextField
          label={t('event.title')}
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
          fullWidth
        />
        <TextField
          label={t('event.location')}
          value={location}
          onChange={(e) => setLocation(e.target.value)}
          required
          fullWidth
        />
        <TextField
          type="date"
          label={t('event.date')}
          value={date}
          slotProps={{ inputLabel: { shrink: true } }}
          onChange={(e) => setDate(e.target.value)}
          required
          fullWidth
        />
        <TextField
          type="time"
          label={t('event.time')}
          value={time}
          slotProps={{ inputLabel: { shrink: true } }}
          onChange={(e) => setTime(e.target.value)}
          required
          fullWidth
        />
        <TextField
          label={t('event.description')}
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          multiline
          rows={3}
          required
          fullWidth
        />
        <FormControlLabel
          control={<Checkbox checked={online} onChange={(e) => setOnline(e.target.checked)} />}
          label="Online Event"
        />
        <Button
          variant="contained"
          color="primary"
          type="submit"
          onClick={handleSubmit}
          disabled={isCreating || isUpdating}
        >
          {eventId ? t('buttons.update') : t('buttons.create')}
        </Button>
        {(isCreating || isUpdating) && <CircularProgress />}
        {isCreateError && (
          <Typography color="error">
            {t('common.error')}{' '}
            {createError instanceof Error ? createError.message : t('common.error.couldntCreateEvent')}
          </Typography>
        )}
        {isUpdateError && (
          <Typography color="error">
            {t('common.error')}{' '}
            {updateError instanceof Error ? updateError.message : t('common.error.couldntUpdateEvent')}
          </Typography>
        )}
      </Stack>
    </Box>
  );
}

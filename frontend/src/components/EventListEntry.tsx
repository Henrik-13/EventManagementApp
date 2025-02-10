import { useNavigate } from 'react-router-dom';
import { Card, CardContent, Chip, Grid2, Typography } from '@mui/material';
import { useTranslation } from 'react-i18next';
import { Event } from '../model/event.model';

type EventListEntryProps = {
  event: Event;
};

export default function EventListEntry({ event }: EventListEntryProps) {
  const navigate = useNavigate();
  const { t } = useTranslation();

  const handleClick = () => {
    navigate(`/event_details/${event.id}`);
  };

  return (
    <Grid2 size={{ xs: 8, sm: 4, md: 2 }}>
      <Card onClick={handleClick} sx={{ cursor: 'pointer' }}>
        <CardContent>
          <Typography variant="h6" gutterBottom>
            {event.title}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            {t('event.location')}: {event.location}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            {t('event.date')}: {event.date}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            {t('event.time')}: {event.time}
          </Typography>
          <Chip
            label={event.online ? t('event.online') : t('event.offline')}
            color={event.online ? 'success' : 'default'}
            size="small"
            sx={{ mt: 2 }}
          />
        </CardContent>
      </Card>
    </Grid2>
  );
}

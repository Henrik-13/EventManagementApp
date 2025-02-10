import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Box, Button, Card, CardContent, CircularProgress, Divider, Stack, Typography } from '@mui/material';
import PeopleIcon from '@mui/icons-material/People';
import EventIcon from '@mui/icons-material/Event';
import AccessTimeIcon from '@mui/icons-material/AccessTime';
import LocationOnIcon from '@mui/icons-material/LocationOn';
import DescriptionIcon from '@mui/icons-material/Description';
import WifiIcon from '@mui/icons-material/Wifi';
import { useTranslation } from 'react-i18next';
import ParticipantDetails from './ParticipantDetails';
import ConfirmDialog from './ConfirmDialog';
import useFindEventById from '../hooks/useFindEventById';
import useDeleteEvent from '../hooks/useDeleteEvent';

export default function EventDetails() {
  const { t } = useTranslation();
  const [isDialogOpen, setDialogOpen] = useState<boolean>(false);
  const { id } = useParams();
  const navigate = useNavigate();
  const { data: event, isLoading } = useFindEventById(id ? parseInt(id, 10) : 0);
  const { mutate: deleteEvent } = useDeleteEvent();

  const handleDelete = () => {
    deleteEvent(parseInt(id!, 10));
    navigate('/');
  };

  const handleUpdate = () => {
    navigate(`/create_event?update=${id}`);
  };

  if (isLoading) {
    return <CircularProgress />;
  }

  if (!event) {
    return <Typography variant="h6">{t('common.couldntLoadEvent')}</Typography>;
  }

  return (
    <Box sx={{ maxWidth: 600, margin: 'auto', borderRadius: 2, p: 2 }}>
      <Card>
        <CardContent>
          <Typography variant="h5" gutterBottom>
            {event?.title}
          </Typography>
          <Divider sx={{ my: 2 }} />
          <Stack direction="row" spacing={1} alignItems="center" sx={{ mb: 2 }}>
            <EventIcon color="primary" />
            <Typography variant="body1">
              {t('event.date')}: {event?.date}
            </Typography>
          </Stack>
          <Stack direction="row" spacing={1} alignItems="center" sx={{ mb: 2 }}>
            <AccessTimeIcon color="primary" />
            <Typography variant="body1">
              {t('event.time')}: {event?.time}
            </Typography>
          </Stack>
          <Stack direction="row" spacing={1} alignItems="center" sx={{ mb: 2 }}>
            <LocationOnIcon color="primary" />
            <Typography variant="body1">
              {t('event.location')}: {event?.location}
            </Typography>
          </Stack>
          <Stack direction="row" spacing={1} alignItems="center" sx={{ mb: 2 }}>
            <DescriptionIcon color="primary" />
            <Typography variant="body1">
              {t('event.description')}: {event?.description}
            </Typography>
          </Stack>
          <Stack direction="row" spacing={1} alignItems="center" sx={{ mb: 2 }}>
            {event?.online ? (
              <>
                <WifiIcon color="primary" />
                <Typography variant="body1">{t('event.online')}</Typography>
              </>
            ) : (
              <>
                <PeopleIcon color="primary" />
                <Typography variant="body1">{t('event.offline')}</Typography>
              </>
            )}
          </Stack>

          <Divider sx={{ my: 2 }} />
          <ParticipantDetails eventId={parseInt(id!, 10)} />
          <Divider sx={{ my: 2 }} />

          <Stack direction="row" spacing={2} sx={{ mt: 4 }}>
            <Button variant="contained" color="primary" onClick={handleUpdate}>
              {t('buttons.update')}
            </Button>
            <Button variant="contained" color="error" onClick={() => setDialogOpen(true)}>
              {t('buttons.delete')}
            </Button>
          </Stack>
        </CardContent>
      </Card>

      <ConfirmDialog
        open={isDialogOpen}
        title={t('event.deleteEvent')}
        description={t('event.confirmDelete')}
        onClose={() => setDialogOpen(false)}
        onConfirm={handleDelete}
        confirmText={t('buttons.delete')}
        cancelText={t('buttons.cancel')}
      />
    </Box>
  );
}

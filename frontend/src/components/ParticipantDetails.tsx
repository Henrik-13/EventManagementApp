import { useState } from 'react';
import { Box, Button, Stack, Typography, IconButton, CircularProgress } from '@mui/material';
import PersonIcon from '@mui/icons-material/Person';
import DeleteIcon from '@mui/icons-material/Delete';
import PeopleIcon from '@mui/icons-material/People';
import { useTranslation } from 'react-i18next';
import useFindAllParticipants from '../hooks/useFindAllParticipants';
import useCreateParticipant from '../hooks/useCreateParticipant';
import useDeleteParticipant from '../hooks/useDeleteParticipant';
import { CreateParticipant } from '../model/participant.model';
import AddParticipantDialog from './AddParticipantDialog';
import ConfirmDialog from './ConfirmDialog';

type ParticipantDetailsProps = {
  eventId: number;
};

export default function ParticipantDetails({ eventId }: ParticipantDetailsProps) {
  const { t } = useTranslation();
  const [isAddDialogOpen, setAddDialogOpen] = useState(false);
  const [isDeleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [selectedParticipantId, setSelectedParticipantId] = useState<number | null>(null);
  const [newParticipant, setNewParticipant] = useState<CreateParticipant>({
    name: '',
    email: '',
  });

  const { data: participants, isLoading } = useFindAllParticipants(eventId);
  const { mutate: createParticipant } = useCreateParticipant();
  const { mutate: deleteParticipant } = useDeleteParticipant();

  const handleAddParticipant = () => {
    createParticipant(
      {
        eventId,
        data: newParticipant,
      },
      {
        onSuccess: () => {
          setAddDialogOpen(false);
          setNewParticipant({ name: '', email: '' });
        },
      },
    );
  };

  const handleDeleteParticipant = (participantId: number) => {
    deleteParticipant({ eventId, participantId });
    setDeleteDialogOpen(false);
  };

  if (isLoading) {
    return <CircularProgress />;
  }

  return (
    <Box>
      <Stack direction="row" spacing={1} alignItems="center" sx={{ mb: 2 }}>
        <PeopleIcon color="primary" />
        <Typography variant="h6">
          {t('participant.participants')} ({participants?.length || 0})
        </Typography>
        <Button variant="contained" size="small" onClick={() => setAddDialogOpen(true)} sx={{ ml: 'auto' }}>
          {t('participant.addParticipant')}
        </Button>
      </Stack>

      {participants && participants.length > 0 ? (
        <Stack spacing={1} sx={{ ml: 4 }}>
          {participants.map((participant) => (
            <Stack
              key={participant.id}
              direction="row"
              spacing={1}
              alignItems="center"
              sx={{
                p: 1,
                '&:hover': { bgcolor: 'action.hover' },
              }}
            >
              <PersonIcon fontSize="small" />
              <Typography>
                {participant.name} ({participant.email})
              </Typography>
              <IconButton
                size="small"
                color="error"
                onClick={() => {
                  setSelectedParticipantId(participant.id);
                  setDeleteDialogOpen(true);
                }}
                sx={{ ml: 'auto' }}
              >
                <DeleteIcon fontSize="small" />
              </IconButton>
            </Stack>
          ))}
        </Stack>
      ) : (
        <Typography variant="body2" sx={{ ml: 4, fontStyle: 'italic' }}>
          {t('participant.noParticipants')}
        </Typography>
      )}

      <AddParticipantDialog
        open={isAddDialogOpen}
        onClose={() => setAddDialogOpen(false)}
        onAdd={handleAddParticipant}
        participant={newParticipant}
        onParticipantChange={setNewParticipant}
      />
      <ConfirmDialog
        open={isDeleteDialogOpen}
        title={t('participant.deleteParticipant')}
        description={t('participant.confirmDeleteParticipant')}
        onClose={() => setDeleteDialogOpen(false)}
        onConfirm={() => selectedParticipantId && handleDeleteParticipant(selectedParticipantId)}
        confirmText={t('buttons.delete')}
        cancelText={t('buttons.cancel')}
      />
    </Box>
  );
}

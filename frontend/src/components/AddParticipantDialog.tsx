import { Button, Dialog, DialogTitle, DialogContent, DialogActions, TextField, Stack } from '@mui/material';
import { useTranslation } from 'react-i18next';
import { CreateParticipant } from '../model/participant.model';

type AddParticipantDialogProps = {
  open: boolean;
  onClose: () => void;
  onAdd: (participant: CreateParticipant) => void;
  participant: CreateParticipant;
  onParticipantChange: (participant: CreateParticipant) => void;
};

export default function AddParticipantDialog({
  open,
  onClose,
  onAdd,
  participant,
  onParticipantChange,
}: AddParticipantDialogProps) {
  const { t } = useTranslation();
  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>{t('participant.addParticipant')}</DialogTitle>
      <DialogContent>
        <Stack spacing={2} sx={{ mt: 1, minWidth: 300 }}>
          <TextField
            label="Name"
            value={participant.name}
            onChange={(e) => onParticipantChange({ ...participant, name: e.target.value })}
            fullWidth
          />
          <TextField
            label="Email"
            type="email"
            value={participant.email}
            onChange={(e) => onParticipantChange({ ...participant, email: e.target.value })}
            fullWidth
          />
        </Stack>
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>{t('buttons.cancel')}</Button>
        <Button
          onClick={() => onAdd(participant)}
          variant="contained"
          disabled={!participant.name || !participant.email}
        >
          {t('buttons.add')}
        </Button>
      </DialogActions>
    </Dialog>
  );
}

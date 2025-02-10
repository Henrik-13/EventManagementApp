import { useState } from 'react';
import { Box, Stack, TextField, FormControlLabel, Checkbox, Button } from '@mui/material';
import { useTranslation } from 'react-i18next';
import { EventFilter } from '../model/eventfilter.model';

type EventFilterProps = {
  onFilter: (filter: EventFilter) => void;
};

export default function EventFilterForm({ onFilter }: EventFilterProps) {
  const { t } = useTranslation();
  const [filter, setFilter] = useState<EventFilter>({
    titlePattern: '',
    minDate: '',
    maxDate: '',
    minTime: '',
    maxTime: '',
    online: undefined,
  });

  const handleChange = (field: keyof EventFilter, value: string | boolean | undefined) => {
    setFilter((prev) => ({ ...prev, [field]: value }));
  };

  const handleSubmit = () => {
    onFilter(filter);
  };

  return (
    <Box
      component="form"
      sx={{
        maxWidth: 1200,
        // margin: 'auto',
        mt: 2,
        mb: 2,
        backgroundColor: (theme) => (theme.palette.mode === 'dark' ? '#000000' : theme.palette.background.default),
        borderRadius: 2,
        p: 2,
        pb: 0.3,
      }}
      onSubmit={(e) => e.preventDefault()}
    >
      <Stack direction="row" spacing={2} alignItems="center" sx={{ mb: 2 }}>
        <TextField
          label={t('filter.title')}
          value={filter.titlePattern}
          onChange={(e) => handleChange('titlePattern', e.target.value)}
          size="small"
        />
        <TextField
          label={t('filter.minDate')}
          type="date"
          slotProps={{ inputLabel: { shrink: true } }}
          value={filter.minDate}
          onChange={(e) => handleChange('minDate', e.target.value)}
          size="small"
        />
        <TextField
          label={t('filter.maxDate')}
          type="date"
          slotProps={{ inputLabel: { shrink: true } }}
          value={filter.maxDate}
          onChange={(e) => handleChange('maxDate', e.target.value)}
          size="small"
        />
        <TextField
          label={t('filter.minTime')}
          type="time"
          slotProps={{ inputLabel: { shrink: true } }}
          value={filter.minTime}
          onChange={(e) => handleChange('minTime', e.target.value)}
          size="small"
        />
        <TextField
          label={t('filter.maxTime')}
          type="time"
          slotProps={{ inputLabel: { shrink: true } }}
          value={filter.maxTime}
          onChange={(e) => handleChange('maxTime', e.target.value)}
          size="small"
        />
        <FormControlLabel
          control={
            <Checkbox
              checked={filter.online === true}
              onChange={(e) => handleChange('online', e.target.checked ? true : undefined)}
            />
          }
          label={t('filter.online')}
        />

        <Button variant="contained" color="primary" onClick={handleSubmit}>
          {t('filter.apply')}
        </Button>
      </Stack>
    </Box>
  );
}

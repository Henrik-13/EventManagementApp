import { Box, Select, MenuItem, SelectChangeEvent, useTheme } from '@mui/material';
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';

const languages = [
  { code: 'en', name: 'common.languages.english', flag: '🇬🇧' },
  { code: 'hu', name: 'common.languages.hungarian', flag: '🇭🇺' },
  { code: 'de', name: 'common.languages.german', flag: '🇩🇪' },
  { code: 'ro', name: 'common.languages.romanian', flag: '🇷🇴' },
];

export default function LanguageSwitcher() {
  const { t, i18n } = useTranslation();
  const theme = useTheme();
  const [language, setLanguage] = useState<string>(localStorage.getItem('language') || 'en');

  useEffect(() => {
    setLanguage(i18n.language);
    localStorage.setItem('language', i18n.language);
  }, [i18n.language]);

  const handleChange = (event: SelectChangeEvent<string>) => {
    i18n.changeLanguage(event.target.value);
  };

  return (
    <Box sx={{ minWidth: 150 }}>
      <Select
        sx={{
          border: 'none',
        }}
        value={language}
        onChange={handleChange}
        size="small"
        displayEmpty
        renderValue={(selected) => {
          const selectedLang = languages.find((lang) => lang.code === selected);
          return (
            <Box
              sx={{
                display: 'flex',
                alignItems: 'center',
                gap: 1,
                color: theme.palette.mode === 'light' ? 'white' : 'inherit',
              }}
            >
              <span role="img" aria-label={selectedLang?.name}>
                {selectedLang?.flag}
              </span>
              {selectedLang ? t(selectedLang.name) : ''}
            </Box>
          );
        }}
      >
        {languages.map((lang) => (
          <MenuItem key={lang.code} value={lang.code}>
            <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
              <span role="img" aria-label={lang.name} style={{ fontSize: '1.2rem' }}>
                {lang.flag}
              </span>
              {t(lang.name)}
            </Box>
          </MenuItem>
        ))}
      </Select>
    </Box>
  );
}

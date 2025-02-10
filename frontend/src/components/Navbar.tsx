import { AppBar, Box, Button, Toolbar, Typography } from '@mui/material';
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import LanguageSwitcher from './LanguageSwitcher';

export default function Navbar() {
  const { t } = useTranslation();
  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            {t('common.calendar')}
          </Typography>
          <Button color="inherit" component={Link} to="/">
            {t('common.home')}
          </Button>
          <Button color="inherit" component={Link} to="create_event">
            {t('common.create')}
          </Button>
          <LanguageSwitcher />
        </Toolbar>
      </AppBar>
    </Box>
  );
}

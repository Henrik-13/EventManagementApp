import { IconButton } from '@mui/material';
import LightModeIcon from '@mui/icons-material/LightMode';
import DarkModeIcon from '@mui/icons-material/DarkMode';
import ContrastIcon from '@mui/icons-material/Contrast';
import { ThemeType, themeMap } from '../theme/themes';

type ThemeSwitcherProps = {
  themeType: ThemeType;
  setThemeType: (theme: ThemeType) => void;
};

export default function ThemeSwitcher({ themeType, setThemeType }: ThemeSwitcherProps) {
  const handleToggleTheme = () => {
    const nextTheme = themeMap[themeType];
    setThemeType(nextTheme);
  };

  const renderIcon = () => {
    switch (themeType) {
      case 'light':
        return <LightModeIcon sx={{ width: 48, height: 48 }} />;
      case 'dark':
        return <DarkModeIcon sx={{ width: 48, height: 48 }} />;
      case 'contrast':
        return <ContrastIcon sx={{ width: 48, height: 48 }} />;
      default:
        return <LightModeIcon sx={{ width: 48, height: 48 }} />;
    }
  };

  return (
    <IconButton sx={{ position: 'fixed', cursor: 'pointer', bottom: 10, left: 10 }} onClick={handleToggleTheme}>
      {renderIcon()}
    </IconButton>
  );
}

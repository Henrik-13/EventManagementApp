import { ThemeOptions } from '@mui/material';
import blue from '@mui/material/colors/blue.js';
import grey from '@mui/material/colors/grey.js';

export type ThemeType = 'light' | 'dark' | 'contrast';

export const themeMap: Record<ThemeType, ThemeType> = {
  dark: 'light',
  light: 'contrast',
  contrast: 'dark',
};

export const themeOptionsByType: Record<ThemeType, ThemeOptions> = {
  light: {
    palette: {
      mode: 'light',
      secondary: {
        main: blue[900],
      },
    },
  },
  dark: {
    palette: {
      mode: 'dark',
      secondary: {
        main: blue[200],
      },
    },
  },
  contrast: {
    palette: {
      mode: 'dark',
      primary: {
        main: grey[700],
        light: grey[400],
        dark: grey[900],
      },
      secondary: {
        main: grey[800],
      },
      background: {
        default: '#bcbcbc',
        paper: '#000000',
      },
      text: {
        primary: '#FFD700',
        secondary: '#FFA500',
      },
    },
    components: {
      MuiTypography: {
        styleOverrides: {
          h4: {
            color: '#000000',
          },
        },
      },
    },
  },
};

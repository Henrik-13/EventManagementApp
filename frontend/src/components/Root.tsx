import { QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { useState, useMemo, useEffect } from 'react';
import { createTheme, CssBaseline, ThemeProvider } from '@mui/material';
import { themeOptionsByType, ThemeType } from '../theme/themes';
import Navbar from './Navbar';
import EventCreationForm from './EventCreationForm';
import EventList from './EventList';
import EventDetails from './EventDetails';
import { queryClient } from '../query/common.query';
import ThemeSwitcher from './ThemeSwitcher';

export default function Root() {
  const [themeType, setThemeType] = useState<ThemeType>(() => {
    const theme = localStorage.getItem('theme');
    return theme ? (theme as ThemeType) : 'light';
  });

  const theme = useMemo(() => {
    const themeOptions = themeOptionsByType[themeType];
    return createTheme(themeOptions);
  }, [themeType]);

  useEffect(() => {
    localStorage.setItem('theme', themeType);
  }, [themeType]);

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <QueryClientProvider client={queryClient}>
        <BrowserRouter>
          <Navbar />
          <Routes>
            <Route path="/" element={<EventList />} />
            <Route path="/create_event" element={<EventCreationForm />} />
            <Route path="/event_details/:id" element={<EventDetails />} />
          </Routes>
        </BrowserRouter>
        <ThemeSwitcher themeType={themeType} setThemeType={setThemeType} />
        <ReactQueryDevtools initialIsOpen={false} />
      </QueryClientProvider>
    </ThemeProvider>
  );
}

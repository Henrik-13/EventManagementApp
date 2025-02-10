import { initReactI18next } from 'react-i18next';
import i18n from 'i18next';
import { en } from './internationalization/en';
import { hu } from './internationalization/hu';
import { de } from './internationalization/de';
import { ro } from './internationalization/ro';

const savedLanguage = localStorage.getItem('language') || 'en';

i18n.use(initReactI18next).init({
  resources: {
    en: { translation: en },
    hu: { translation: hu },
    de: { translation: de },
    ro: { translation: ro },
  },
  lng: savedLanguage,
  fallbackLng: 'en',
  interpolation: {
    escapeValue: false,
  },
});

export default i18n;

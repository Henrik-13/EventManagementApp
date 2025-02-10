import { createRoot } from 'react-dom/client';
import Root from './components/Root';
import './i18n';

const root = document.getElementById('root');

if (!root) {
  throw new Error('Could not find root element');
}

createRoot(root).render(<Root />);

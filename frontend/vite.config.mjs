import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import { VitePWA } from 'vite-plugin-pwa';

const reactPlugin = react();

const pwaPlugin = VitePWA({
  registerType: 'autoUpdate',
  name: 'Calendar App',
  manifest: {
    name: 'Calendar App',
    start_url: '/',
    display: 'standalone',
    background_color: '#ffffff',
    scope: '/',
    icons: [
      { src: '/icons/icon-32.png', sizes: '32x32', type: 'image/png' },
      { src: '/icons/icon-128.png', sizes: '128x128', type: 'image/png' },
      { src: '/icons/icon-512.png', sizes: '512x512', type: 'image/png' },
    ],
    workbox: {
      runtimeCaching: [
        {
          urlPattern: ({ request }) => request.destination === 'image',
          handler: 'CacheFirst',
          options: {
            cacheName: 'images-cache',
            expiration: {
              maxEntries: 50,
              maxAgeSeconds: 30 * 24 * 60 * 60,
            },
          },
        },
        {
          urlPattern: ({ request }) => request.destination === 'script' || request.destination === 'style',
          handler: 'StaleWhileRevalidate',
          options: {
            cacheName: 'static-resources',
          },
        },
      ],
    },
  },
});

const config = defineConfig({
  plugins: [reactPlugin, pwaPlugin],
});

export default config;

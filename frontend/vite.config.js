import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  base: '/online-job-portal/',
  server: {
    port: 5173
  }
});

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    host: true,
    // WSL : le disque Windows (/mnt/c) n'émet pas d'événements de fichier -> polling.
    watch: { usePolling: true },
  },
})

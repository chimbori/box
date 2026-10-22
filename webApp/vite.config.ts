import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
  plugins: [react()],
  build: {
    // The Kotlin/JS shared build is UMD (CommonJS-style), so it needs CommonJS interop.
    commonjsOptions: {
      include: [/node_modules/, /shared\/build\/dist\/js/],
    },
  },
  server: {
    port: 8081,
  },
  optimizeDeps: {
    include: ["box-shared"],
  },
});

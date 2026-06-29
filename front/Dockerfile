# --- Étape 1 : build de l'app Vue ---
FROM node:22-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
# URL d'API relative : les appels passent par nginx (même origine) -> pas de CORS.
ENV VITE_API_URL=""
RUN npm run build

# --- Étape 2 : service statique avec nginx ---
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80

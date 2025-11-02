# =========================================
# Stage 1: Build the Angular Application
# =========================================

FROM node:20-alpine as build

# Set working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN --mount=type=cache,target=/root/.npm npm ci

# Copy project files
COPY . .

# Build app
RUN npm run build && \
    if [ -d "dist/frontend/browser" ]; then cp -a dist/frontend/browser/. dist/frontend/; fi && \
    if [ -d "public" ]; then cp -a public/. dist/frontend/; fi

# =========================================
# Stage 2: Prepare Nginx to Serve Static Files
# =========================================

FROM nginx:alpine

# Copy built app to nginx html directory
COPY --from=build /app/dist/frontend/ /usr/share/nginx/html/

# Copy nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80

# Start nginx
CMD ["nginx", "-g", "daemon off;"]
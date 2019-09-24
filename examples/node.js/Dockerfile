FROM node:10

# Create app directory
WORKDIR /usr/src/app

# Install app dependencies
# A wildcard is used to ensure both package.json AND package-lock.json are copied
# where available (npm@5+)
COPY package*.json ./
RUN npm install --only=production

# Bundle app source
COPY . .

# Expose a TCP port for incoming traffic
EXPOSE 8080

# Run our application
CMD [ "node", "app.js" ]

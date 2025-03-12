# Define directories
CLIENT_DIR = client
SERVER_DIR = api

# Default target
.PHONY: all
all: install build-server build-client

# Install dependencies for both client and server
.PHONY: install
install:
	cd $(CLIENT_DIR) && pnpm install
	cd $(SERVER_DIR) && mvn clean install -DskipTests

# Build the server-side app using Maven
.PHONY: build-server
build-server:
	cd $(SERVER_DIR) && mvn package -DskipTests

# Build the Vue 3 client app for production
.PHONY: build-client
build-client:
	cd $(CLIENT_DIR) && pnpm build

# Clean both client and server builds
.PHONY: clean
clean:
	rm -rf $(CLIENT_DIR)/dist
	cd $(SERVER_DIR) && mvn clean

# Serve the built client project locally (optional)
.PHONY: serve
serve:
	cd $(CLIENT_DIR) && pnpm run preview

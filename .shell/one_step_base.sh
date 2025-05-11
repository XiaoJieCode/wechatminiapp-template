#!/bin/sh

# 设置环境变量（默认为dev）
ENV=${ENV:-prod}

echo "[INFO] Creating directories for environment: $ENV ..."

# 根路径
BASE_DIR="/server/$ENV"

# 创建 MySQL 相关目录
echo "[INFO] Creating MySQL directories ..."
mkdir -p "$BASE_DIR/docker/mysql/data"
mkdir -p "$BASE_DIR/docker/mysql/log"
mkdir -p "$BASE_DIR/docker/mysql/conf"
chown -R 999:999 "$BASE_DIR/docker/mysql/data"
chown -R 999:999 "$BASE_DIR/docker/mysql/log"
chown -R 999:999 "$BASE_DIR/docker/mysql/conf"
# 创建 MinIO 相关目录
echo "[INFO] Creating MinIO directories ..."
mkdir -p "$BASE_DIR/docker/minio/data"

# 创建 Redis 相关目录
echo "[INFO] Creating Redis directories ..."
mkdir -p "$BASE_DIR/docker/redis/data"

# 创建 RabbitMQ 相关目录
echo "[INFO] Creating RabbitMQ directories ..."
mkdir -p "$BASE_DIR/docker/rabbitmq/data"

# 创建 Nacos 相关目录
echo "[INFO] Creating Nacos directories ..."
mkdir -p "$BASE_DIR/docker/nacos/conf"

# 创建 Nginx 相关目录
echo "[INFO] Creating Nginx directories ..."
mkdir -p "$BASE_DIR/nginx/conf"
mkdir -p "$BASE_DIR/nginx/conf.d"
mkdir -p "$BASE_DIR/nginx/html"
mkdir -p "$BASE_DIR/nginx/logs"
mkdir -p "$BASE_DIR/nginx/ssl"

# 创建默认的 nginx.conf
if [ ! -f "$BASE_DIR/nginx/conf/nginx.conf" ]; then
  echo "[INFO] Creating default nginx.conf ..."
  cat > "$BASE_DIR/nginx/conf/nginx.conf" <<EOF
worker_processes  1;

events {
    worker_connections  1024;
}

http {
    include       mime.types;
    default_type  application/octet-stream;

    log_format  main  '\$remote_addr - \$remote_user [\$time_local] "\$request" '
                      '\$status \$body_bytes_sent "\$http_referer" '
                      '"\$http_user_agent" "\$http_x_forwarded_for"';

    access_log  /var/log/nginx/access.log  main;
    error_log   /var/log/nginx/error.log warn;

    sendfile        on;
    keepalive_timeout  65;

    include /server/$ENV/nginx/conf.d/*.conf;
}
EOF
fi

# 创建默认反向代理配置：默认的API代理
if [ ! -f "$BASE_DIR/nginx/conf.d/default.conf" ]; then
  echo "[INFO] Creating default reverse proxy configuration ..."
  cat > "$BASE_DIR/nginx/conf.d/default.conf" <<EOF
server {
    listen       80;
    server_name  localhost;

    location / {
        root   /server/$ENV/nginx/html;
        index  index.html index.htm;
    }

    location /api/ {
        proxy_pass http://sgkq-api-server:8080/;
        proxy_set_header Host \$host;
        proxy_set_header X-Real-IP \$remote_addr;
        proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto \$scheme;
    }
}
EOF
fi

# 如果是prod环境，创建额外的 SSL 配置（示例）
if [ "$ENV" = "prod" ]; then
  echo "[INFO] Creating SSL configuration for production ..."
  cat > "$BASE_DIR/nginx/conf.d/ssl.conf" <<EOF
server {
    listen 443 ssl;
    server_name example.com;

    ssl_certificate /server/$ENV/nginx/ssl/example.com.crt;
    ssl_certificate_key /server/$ENV/nginx/ssl/example.com.key;

    location / {
        root /server/$ENV/nginx/html;
        index index.html;
    }
}
EOF
fi

# 设置权限
echo "[INFO] Setting permissions..."
chmod -R 755 /server/$ENV

echo "[DONE] All directories and default config for '$ENV' are ready."

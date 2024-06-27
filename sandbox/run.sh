# Criar as docker networks
docker network create adm_videos_services
docker network create elastic
echo "Criando network..."
sleep 1

# Criar as pastas com permissões
#sudo chown root app/filebeat/filebeat.docker.yml
#mkdir -m 777 .docker
#mkdir -m 777 .docker/es01
#mkdir -m 777 .docker/keycloak
#mkdir -m 777 .docker/filebeat

docker compose -f services/docker-compose.yml up -d
docker compose -f elk/docker-compose.yml up -d
echo "Inicializando os containers..."
sleep 20

docker compose -f app/docker-compose.yml up -d --build
#command to run and force the rebuild
#docker compose -f app/docker-compose.yml up -d --build
echo "Inicializando aplicação..."
sleep 1

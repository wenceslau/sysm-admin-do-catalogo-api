# Criar as docker networks
docker network create adm_videos_services
echo "Criando network..."
sleep 1

# Criar as pastas com permissões
#mkdir -m 777 .docker
#mkdir -m 777 .docker/keycloak

docker compose -f services/docker-compose.yml up -d
echo "Inicializando os containers..."
sleep 20

docker compose -f app/docker-compose.yml up -d
#command to run and force the rebuild
#docker compose -f app/docker-compose.yml up -d --build
echo "Inicializando os aplicação..."
sleep 1
echo "finalizando aplicação..."
docker compose -f app/docker-compose.yml down
sleep 5

echo "finalizando services..."
docker compose -f services/docker-compose.yml down
docker compose -f elk/docker-compose.yml down
sleep 5

echo "Removendo network..."
docker network rm adm_videos_services
docker network rm elastic
sleep 1
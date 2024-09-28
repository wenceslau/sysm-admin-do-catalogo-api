#!/bin/bash
echo "Creating network..."

docker network create ntw_keycloak
docker network create ntw_elastic
docker network create ntw_adm_catalog

docker volume create vol_keycloak
docker volume create vol_adm_catalog_mysql_prd
docker volume create vol_adm_catalog_elastic_01_prd
docker volume create vol_adm_catalog_logstash_prd
docker volume create vol_adm_catalog_filebeat_prd

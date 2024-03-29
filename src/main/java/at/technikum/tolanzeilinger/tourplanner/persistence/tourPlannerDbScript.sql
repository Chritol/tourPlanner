-- docker run --name postgres -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_HOST_AUTH_METHOD=trust -e POSTGRES_DB=simpledatastore -d postgres:latest

-- docker exec -it postgres psql -U postgres simpledatastore

-- DROP DATABASE IF EXISTS tourplanner_db;
-- CREATE DATABASE tourplanner_db;

-- \connect tourplanner_db;


DROP TABLE IF EXISTS tp_tour CASCADE;
CREATE TABLE tp_tour (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50),
  description TEXT,
  destination_from VARCHAR(100),
  destination_to VARCHAR(100),
  transportation_type VARCHAR(50),
  distance INT,
  estimated_time INT,
  hill_type varchar(50),
  popularity varchar(50),
  child_friendliness varchar(50)
);

DROP TABLE IF EXISTS tp_tour_Log CASCADE;
CREATE TABLE tp_tour_Log (
  id SERIAL PRIMARY KEY,
  tour_id INTEGER NOT NULL,
  log_datetime TIMESTAMP,
  comment TEXT,
  difficulty VARCHAR(50),
  total_time INT,
  rating INT,
  FOREIGN KEY (tour_id) REFERENCES tp_tour(id)
);

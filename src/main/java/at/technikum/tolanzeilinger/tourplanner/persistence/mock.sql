INSERT INTO tp_tour (name, description, destination_from, destination_to, transportation_type, distance, estimated_time, hill_type, popularity, child_friendliness)
VALUES ('City Center Stroll', 'Explore the vibrant city center', 'New York', 'Central Park', 'WALK', 3, 45, 'DEFAULT_STRATEGY', 'OVER_100', 'RECOMMENDED_FOR_CHILDREN');

INSERT INTO tp_tour_Log (tour_id, log_datetime, comment, difficulty, total_time, rating)
VALUES (1, '2023-06-01 10:00:00', 'Had a great time exploring the bustling streets!', 'EASY', 60, 4);

INSERT INTO tp_tour_Log (tour_id, log_datetime, comment, difficulty, total_time, rating)
VALUES (1, '2023-06-02 14:30:00', 'Enjoyed the beautiful park and its tranquility.', 'EASY', 45, 5);

INSERT INTO tp_tour_Log (tour_id, log_datetime, comment, difficulty, total_time, rating)
VALUES (1, '2023-06-03 12:15:00', 'A wonderful way to experience the city.', 'EASY', 50, 4);

INSERT INTO tp_tour (name, description, destination_from, destination_to, transportation_type, distance, estimated_time, hill_type, popularity, child_friendliness)
VALUES ('Coastal Bike Adventure', 'Ride along the scenic coastal route', 'Los Angeles', 'Santa Monica Pier', 'BIKE', 15, 60, 'FAVOR_DOWN_HILL', 'OVER_50', 'NOT_RECOMMENDED_FOR_CHILDREN');

INSERT INTO tp_tour_Log (tour_id, log_datetime, comment, difficulty, total_time, rating)
VALUES (2, '2023-06-01 08:00:00', 'Breathtaking views of the Pacific Ocean!', 'INTERMEDIATE', 45, 5);

INSERT INTO tp_tour_Log (tour_id, log_datetime, comment, difficulty, total_time, rating)
VALUES (2, '2023-06-02 11:30:00', 'Challenging but exhilarating ride!', 'HARD', 60, 4);

INSERT INTO tp_tour_Log (tour_id, log_datetime, comment, difficulty, total_time, rating)
VALUES (2, '2023-06-03 10:45:00', 'Made it to the pier and enjoyed the lively atmosphere.', 'INTERMEDIATE', 75, 3);

INSERT INTO tp_tour (name, description, destination_from, destination_to, transportation_type, distance, estimated_time, hill_type, popularity, child_friendliness)
VALUES ('Mountain Expedition', 'Scenic car drive through the majestic mountains', 'Denver', 'Rocky Mountains', 'CAR', 200, 180, 'AVOID_ALL_HILLS', 'OVER_10', 'RECOMMENDED_FOR_CHILDREN');

INSERT INTO tp_tour_Log (tour_id, log_datetime, comment, difficulty, total_time, rating)
VALUES (3, '2023-06-01 09:00:00', 'Unforgettable panoramic views!', 'EASY', 120, 5);

INSERT INTO tp_tour_Log (tour_id, log_datetime, comment, difficulty, total_time, rating)
VALUES (3, '2023-06-02 13:30:00', 'An epic journey through natures wonders.', 'EASY', 150, 4);

INSERT INTO tp_tour_Log (tour_id, log_datetime, comment, difficulty, total_time, rating)
VALUES (3, '2023-06-03 11:15:00', 'The kids were amazed by the towering mountains.', 'EASY', 100, 5);

INSERT INTO tp_tour_Log (tour_id, log_datetime, comment, difficulty, total_time, rating)
VALUES (3, '2023-06-04 14:00:00', 'A memorable trip with breathtaking vistas.', 'EASY', 180, 5);

INSERT INTO tp_tour (name, description, destination_from, destination_to, transportation_type, distance, estimated_time, hill_type, popularity, child_friendliness)
VALUES ('Historical Walking Tour', 'Discover the rich history of Rome on foot', 'Colosseum', 'Vatican City', 'WALK', 5, 120, 'DEFAULT_STRATEGY', 'OVER_100', 'RECOMMENDED_FOR_CHILDREN');

INSERT INTO tp_tour_Log (tour_id, log_datetime, comment, difficulty, total_time, rating)
VALUES (4, '2023-06-01 09:30:00', 'Fascinating journey through ancient ruins!', 'EASY', 150, 4);

INSERT INTO tp_tour_Log (tour_id, log_datetime, comment, difficulty, total_time, rating)
VALUES (4, '2023-06-02 11:00:00', 'Immersed in the citys vibrant history.', 'EASY', 120, 5);

INSERT INTO tp_tour_Log (tour_id, log_datetime, comment, difficulty, total_time, rating)
VALUES (4, '2023-06-03 15:45:00', 'The Vatican was awe-inspiring!', 'EASY', 180, 5);

INSERT INTO tp_tour (name, description, destination_from, destination_to, transportation_type, distance, estimated_time, hill_type, popularity, child_friendliness)
VALUES ('Nature Hike', 'Explore the scenic trails of Banff National Park', 'Banff', 'Lake Louise', 'WALK', 10, 180, 'AVOID_ALL_HILLS', 'OVER_50', 'RECOMMENDED_FOR_CHILDREN');

INSERT INTO tp_tour_Log (tour_id, log_datetime, comment, difficulty, total_time, rating)
VALUES (5, '2023-06-01 10:30:00', 'Breath-taking views of the Canadian Rockies!', 'INTERMEDIATE', 240, 5);

INSERT INTO tp_tour_Log (tour_id, log_datetime, comment, difficulty, total_time, rating)
VALUES (5, '2023-06-02 13:15:00', 'A challenging hike, but worth every step.', 'HARD', 300, 4);

INSERT INTO tp_tour_Log (tour_id, log_datetime, comment, difficulty, total_time, rating)
VALUES (5, '2023-06-03 12:45:00', 'The turquoise waters of Lake Louise were stunning!', 'INTERMEDIATE', 180, 5);

INSERT INTO tp_tour_Log (tour_id, log_datetime, comment, difficulty, total_time, rating)
VALUES (5, '2023-06-04 14:30:00', 'An unforgettable journey through natures paradise.', 'HARD', 360, 5);

INSERT INTO tp_tour (name, description, destination_from, destination_to, transportation_type, distance, estimated_time, hill_type, popularity, child_friendliness)
VALUES ('Wine Tasting Tour', 'Experience the vineyards of Napa Valley', 'San Francisco', 'Napa Valley', 'CAR', 50, 90, 'DEFAULT_STRATEGY', 'OVER_10', 'NOT_RECOMMENDED_FOR_CHILDREN');

INSERT INTO tp_tour_Log (tour_id, log_datetime, comment, difficulty, total_time, rating)
VALUES (6, '2023-06-01 11:00:00', 'Delicious wines and picturesque vineyards!', 'EASY', 120, 4);

INSERT INTO tp_tour_Log (tour_id, log_datetime, comment, difficulty, total_time, rating)
VALUES (6, '2023-06-02 13:30:00', 'A delightful journey through wine country.', 'EASY', 150, 5);

INSERT INTO tp_tour_Log (tour_id, log_datetime, comment, difficulty, total_time, rating)
VALUES (6, '2023-06-03 15:15:00', 'The wine tastings were exceptional!', 'EASY', 90, 4);

INSERT INTO tp_tour (name, description, destination_from, destination_to, transportation_type, distance, estimated_time, hill_type, popularity, child_friendliness)
VALUES ('Safari Adventure', 'Explore the wildlife in Serengeti National Park', 'Arusha', 'Serengeti National Park', 'CAR', 200, 360, 'AVOID_ALL_HILLS', 'OVER_100', 'NOT_FOR_CHILDREN');

INSERT INTO tp_tour_Log (tour_id, log_datetime, comment, difficulty, total_time, rating)
VALUES (7, '2023-06-01 08:00:00', 'Witnessed amazing wildlife in their natural habitat!', 'INTERMEDIATE', 480, 5);

INSERT INTO tp_tour_Log (tour_id, log_datetime, comment, difficulty, total_time, rating)
VALUES (7, '2023-06-02 10:30:00', 'A thrilling safari experience!', 'INTERMEDIATE', 420, 4);

INSERT INTO tp_tour_Log (tour_id, log_datetime, comment, difficulty, total_time, rating)
VALUES (7, '2023-06-03 12:45:00', 'The vastness of the Serengeti was awe-inspiring.', 'INTERMEDIATE', 360, 5);

INSERT INTO tp_tour_Log (tour_id, log_datetime, comment, difficulty, total_time, rating)
VALUES (7, '2023-06-04 14:30:00', 'An unforgettable adventure with incredible wildlife sightings.', 'INTERMEDIATE', 540, 5);



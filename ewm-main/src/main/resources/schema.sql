CREATE TABLE IF NOT EXISTS users
(
    user_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name    VARCHAR NOT NULL,
    email   VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS location
(
    location_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    lat         DOUBLE PRECISION NOT NULL,
    lon         DOUBLE PRECISION NOT NULL,
    name        VARCHAR,
    radius      DOUBLE PRECISION,
    paid        BOOLEAN
);

CREATE TABLE IF NOT EXISTS favorite_locations
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id     BIGINT REFERENCES users (user_id) ON DELETE CASCADE,
    location_id BIGINT REFERENCES location (location_id) ON DELETE CASCADE,
    UNIQUE (user_id, location_id)
);

CREATE TABLE IF NOT EXISTS categories
(
    category_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name        VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS events
(
    event_id           BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    annotation         VARCHAR   NOT NULL,
    confirmed_requests BIGINT,
    created_on         TIMESTAMP NOT NULL,
    description        VARCHAR   NOT NULL,
    event_date         TIMESTAMP NOT NULL,
    paid               BOOLEAN   NOT NULL,
    participant_limit  BIGINT,
    published_on       TIMESTAMP,
    request_moderation BOOLEAN   NOT NULL,
    title              VARCHAR   NOT NULL,
    views              BIGINT,
    category_id        BIGINT    NOT NULL REFERENCES categories (category_id),
    initiator_id       BIGINT    NOT NULL REFERENCES users (user_id),
    location_id        BIGINT    NOT NULL REFERENCES location (location_id),
    state              VARCHAR   NOT NULL
);

CREATE TABLE IF NOT EXISTS compilations
(
    compilation_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title          VARCHAR NOT NULL UNIQUE,
    pinned         BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS compilations_events
(
    compilation_id BIGINT REFERENCES compilations (compilation_id) ON DELETE CASCADE,
    event_id       BIGINT REFERENCES events (event_id) ON DELETE CASCADE,
    PRIMARY KEY (compilation_id, event_id)
);

CREATE TABLE IF NOT EXISTS requests
(
    request_id   BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    created      TIMESTAMP NOT NULL,
    event_id     BIGINT REFERENCES events (event_id) ON DELETE CASCADE,
    requester_id BIGINT    NOT NULL REFERENCES users (user_id) ON DELETE CASCADE,
    status       VARCHAR   NOT NULL
);

CREATE OR REPLACE FUNCTION distance(lat1 float, lon1 float, lat2 float, lon2 float)
    RETURNS float
AS
$$
DECLARE
    dist float = 0;
    rad_lat1 float;
    rad_lat2 float;
    theta float;
    rad_theta float;
BEGIN
    IF lat1 = lat2 AND lon1 = lon2 THEN
        RETURN dist;
    ELSE
        -- переводим градусы широты в радианы
        rad_lat1 = pi() * lat1 / 180;
        -- переводим градусы долготы в радианы
        rad_lat2 = pi() * lat2 / 180;
        -- находим разность долгот
        theta = lon1 - lon2;
        -- переводим градусы в радианы
        rad_theta = pi() * theta / 180;
        -- находим длину ортодромии
        dist = sin(rad_lat1) * sin(rad_lat2) + cos(rad_lat1) * cos(rad_lat2) * cos(rad_theta);

        IF dist > 1 THEN
            dist = 1;
        END IF;

        dist = acos(dist);
        -- переводим радианы в градусы
        dist = dist * 180 / pi();
        -- переводим градусы в километры
        dist = dist * 60 * 1.8524;

        RETURN dist;
    END IF;
END;
$$
    LANGUAGE PLPGSQL;



CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE vote.poll (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(30),
    description VARCHAR(50),
    identifier UUID DEFAULT uuid_generate_v4()
);

CREATE TABLE vote.option (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    idVote BIGINT REFERENCES vote.poll (id),
    title VARCHAR(50)
);

CREATE TABLE vote.vote (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    idPoll BIGINT REFERENCES vote.poll (id),
    idOption BIGINT REFERENCES vote.option (id)
);

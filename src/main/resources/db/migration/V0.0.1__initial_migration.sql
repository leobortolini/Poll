CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE vote.poll (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(30),
    description VARCHAR(50),
    identifier UUID DEFAULT uuid_generate_v4()
);

CREATE TABLE vote.option (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    idVote INT REFERENCES vote.poll (id),
    title VARCHAR(50)
);

CREATE TABLE vote.poll_option (
    idVote INT REFERENCES vote.poll (id),
    idOption INT REFERENCES vote.option (id)
);

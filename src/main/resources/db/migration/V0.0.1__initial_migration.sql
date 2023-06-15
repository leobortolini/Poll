CREATE TABLE vote.vote (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    description TEXT
);

CREATE TABLE vote.option (
    id SERIAL PRIMARY KEY,
    idVote INTEGER REFERENCES vote.vote (id),
    title TEXT
);

CREATE TABLE vote.vote_option (
    idVote INTEGER REFERENCES vote.vote (id),
    idOption INTEGER REFERENCES vote.option (id)
);

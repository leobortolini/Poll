CREATE TABLE vote.poll (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    title VARCHAR(30),
    description VARCHAR(50)
);

CREATE TABLE vote.option (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    idPoll UUID REFERENCES vote.poll (id),
    title VARCHAR(50)
);

CREATE TABLE vote.vote (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    idPoll UUID REFERENCES vote.poll (id),
    idOption BIGINT REFERENCES vote.option (id)
);

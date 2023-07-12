CREATE TABLE poll (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    title VARCHAR(30),
    description VARCHAR(50)
);

CREATE TABLE option (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    idPoll UUID REFERENCES poll (id),
    title VARCHAR(50)
);

CREATE TABLE vote (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    idPoll UUID REFERENCES poll (id),
    idOption BIGINT REFERENCES option (id)
);

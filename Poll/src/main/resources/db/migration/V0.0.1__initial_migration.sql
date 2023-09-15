CREATE TABLE poll (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    title VARCHAR(300),
    description VARCHAR(1000)
);

CREATE TABLE option (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    idPoll UUID REFERENCES poll (id),
    title VARCHAR(300)
);


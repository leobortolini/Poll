CREATE TABLE vote (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    idPoll UUID,
    idOption UUID
);

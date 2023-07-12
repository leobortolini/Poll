ALTER TABLE option
ADD CONSTRAINT uq_option_poll UNIQUE (idPoll, id);

ALTER TABLE vote
ADD CONSTRAINT fk_vote_option_voteoption
FOREIGN KEY (idPoll, idOption) REFERENCES option(idPoll, id);
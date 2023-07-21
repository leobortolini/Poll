package com.ifrs.edu.br.vote.util.response;

import com.ifrs.edu.br.vote.util.dto.VoteCountDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("PollResponse")
public class PollResponse implements Serializable {
    @Id
    private String identifier;
    private List<VoteCountDTO> votes;
}

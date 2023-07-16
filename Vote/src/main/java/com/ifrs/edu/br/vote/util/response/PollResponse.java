package com.ifrs.edu.br.vote.util.response;

import com.ifrs.edu.br.vote.util.dto.VoteCountDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PollResponse implements Serializable {
    private List<VoteCountDTO> votes;
}

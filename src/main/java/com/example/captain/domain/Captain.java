package com.example.captain.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Document(collection = "captain")
public class Captain {

    @Id
    private Long teamId;

    @NotNull
    private Long participantId;

    @NotNull
    private String participantIdentifier;
}

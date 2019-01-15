package com.example.captain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {
    private MessageAction action;
    private Long teamId;
    private Long participantId;
    private String participantIdentifier;
}

package com.example.captain.controller;

import com.example.captain.domain.Captain;
import com.example.captain.dto.IsExists;
import com.example.captain.dto.Message;
import com.example.captain.dto.MessageAction;
import com.example.captain.dto.OperationResult;
import com.example.captain.repository.CaptainRepository;
import com.example.captain.stream.CaptainStream;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CaptainController {

    private Logger logger = LogManager.getLogger();

    @Autowired
    private CaptainRepository repository;

    @Autowired
    private CaptainStream stream;

    @GetMapping("/v1/captain/{teamId}")
    @ApiOperation(value = "Запрос на то, чтобы ответить, есть ли у команды капитан")
    public MappingJacksonValue getCaptain(
        @PathVariable(value = "teamId") Long teamId
    ) {
        try {
            return new MappingJacksonValue(
                repository.findById(teamId).orElseThrow(() -> new Exception())
            );
        } catch (Exception e) {
            return new MappingJacksonValue(
                new IsExists(false)
            );
        }
    }


    @PostMapping("/v1/setCaptain/{teamId}")
    @ApiOperation(value = "Запрос на то, чтобы стать капитаном")
    public OperationResult setCaptain(
        @PathVariable(value = "teamId") Long teamId,
        @Valid @RequestBody @ApiParam(name = "Captain", required = true) Captain captain
    ) {
        try {
            captain.setTeamId(teamId);
            repository.insert(captain);
            sendMessage(MessageAction.setCaptain, captain);
            return new OperationResult(true);
        } catch (Exception e) {
            return new OperationResult(false);
        }
    }


    @PostMapping("/v1/removeCaptain/{teamId}")
    @ApiOperation(value = "Запрос на отказ быть капитаном")
    public OperationResult removeCaptain(
        @PathVariable(value = "teamId") Long teamId
    ) {
        try {
            Captain captain = repository.findById(teamId).orElseThrow(() -> new Exception());
            repository.deleteById(teamId);
            sendMessage(MessageAction.removeCaptain, captain);
            return new OperationResult(true);
        } catch (Exception e) {
            return new OperationResult(false);
        }
    }

    private void sendMessage(MessageAction action, Captain captain) {
        try {
            Message message = new Message(action, captain.getTeamId(), captain.getParticipantId(), captain.getParticipantIdentifier());
            logger.debug("Send message: " + message);
            stream.outputStream().send(
                    MessageBuilder.withPayload(new ObjectMapper().writeValueAsString(message))
                            .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                            .build()
            );
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

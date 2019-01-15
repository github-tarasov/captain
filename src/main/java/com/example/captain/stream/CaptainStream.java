package com.example.captain.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CaptainStream {
    String OUTPUT_STREAM = "OutputStream";

    @Output(OUTPUT_STREAM)
    MessageChannel outputStream();
}

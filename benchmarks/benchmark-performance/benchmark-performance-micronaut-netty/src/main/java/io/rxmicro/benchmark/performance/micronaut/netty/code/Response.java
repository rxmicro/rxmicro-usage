package io.rxmicro.benchmark.performance.micronaut.netty.code;

import static java.util.Objects.requireNonNull;

public class Response {

    private final String message;

    public Response(final String message) {
        this.message = requireNonNull(message);
    }

    public String getMessage() {
        return message;
    }
}

package ru.practicum.exception;

public class TimestampViolationException extends RuntimeException {
    public TimestampViolationException(String message) {
        super(message);
    }
}

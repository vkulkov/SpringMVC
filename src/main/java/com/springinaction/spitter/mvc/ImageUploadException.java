package com.springinaction.spitter.mvc;

public class ImageUploadException extends RuntimeException {
    ImageUploadException(String message) {
        super(message);
    }

    ImageUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}

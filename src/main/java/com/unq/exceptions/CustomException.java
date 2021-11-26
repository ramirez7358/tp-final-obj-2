package com.unq.exceptions;

public class CustomException extends RuntimeException{

    public CustomException(String message) {
        super(message);
    }

    public static class InsufficientBalanceException extends CustomException{
        public InsufficientBalanceException(String message) {
            super(message);
        }
    }

    public static class UserNotFoundException extends CustomException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

}

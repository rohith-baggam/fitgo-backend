package com.example.fitgo.controller.response;

/**
 * ErrorResponse class is used to represent an error response in the system. 
 * It encapsulates an error message and an HTTP status code, which can be used
 * to send error details back to the client.
 */
public class ErrorResponse {

    /**
     * The error message describing the nature of the error.
     */
    private String message;

    /**
     * The HTTP status code that corresponds to the error.
     */
    private int status;

    /**
     * Constructor for initializing the ErrorResponse object with a message and a status code.
     * 
     * @param message The error message that describes the issue.
     * @param status  The HTTP status code associated with the error.
     */
    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }

    /**
     * Getter for the error message.
     * 
     * @return The error message describing the nature of the error.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter for the error message.
     * 
     * @param message The error message that describes the issue.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter for the HTTP status code.
     * 
     * @return The HTTP status code corresponding to the error.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Setter for the HTTP status code.
     * 
     * @param status The HTTP status code that corresponds to the error.
     */
    public void setStatus(int status) {
        this.status = status;
    }
}

package com.example.fitgo.controller.response;

/**
 * SuccessResponse class is used to represent a successful response in the
 * system.
 * It contains the message and the HTTP status code that will be sent to the
 * client
 * when a request is successfully processed.
 */
public class SuccessResponse {

    /**
     * The message to be returned in the success response.
     * This can be any type of object, such as a success message, data, etc.
     */
    private Object message;

    /**
     * The HTTP status code associated with the success response.
     */
    private int status;

    /**
     * Constructor to initialize the SuccessResponse object with a message and a
     * status code.
     * 
     * @param message The success message or data to be included in the response.
     * @param status  The HTTP status code to be associated with the success
     *                response.
     */
    public SuccessResponse(Object message, int status) {
        this.message = message;
        this.status = status;
    }

    /**
     * Getter for the success message.
     * 
     * @return The message (can be data, success message, etc.) to be sent in the
     *         response.
     */
    public Object getMessage() {
        return message;
    }

    /**
     * Setter for the success message.
     * 
     * @param message The message to be sent in the success response.
     */
    public void setMessage(Object message) {
        this.message = message;
    }

    /**
     * Getter for the HTTP status code.
     * 
     * @return The HTTP status code for the success response (e.g., 200).
     */
    public int getStatus() {
        return status;
    }

    /**
     * Setter for the HTTP status code.
     * 
     * @param status The HTTP status code to be associated with the success
     *               response.
     */
    public void setStatus(int status) {
        this.status = status;
    }
}

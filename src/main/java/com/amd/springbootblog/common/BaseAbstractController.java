package com.amd.springbootblog.common;

import com.amd.springbootblog.data.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class BaseAbstractController {

    protected ResponseEntity<?> prepareResponseEntity(BooleanResultObject resultObject, HttpServletRequest request) {

        ResponseEntity responseEntity;

        if (resultObject.getResponseStatus() == ResponseStatus.OK) {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(resultObject);
        } else if (resultObject.getResponseStatus() == ResponseStatus.INTERNAL_SERVER_ERROR) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorMessageResultObject(new Date(), resultObject.getStatus(), "Internal Server Error!", resultObject.getMessage(), request.getRequestURI()));
        } else if (resultObject.getResponseStatus() == ResponseStatus.NOT_FOUND) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorMessageResultObject(new Date(), resultObject.getStatus(), "Not Found!", resultObject.getMessage(), request.getRequestURI()));
        } else if (resultObject.getResponseStatus() == ResponseStatus.CONFLICT) {
            responseEntity = ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ErrorMessageResultObject(new Date(), resultObject.getStatus(), "Conflict!", resultObject.getMessage(), request.getRequestURI()));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(resultObject);
        }
        return responseEntity;
    }
    protected ResponseEntity<?> prepareResponseEntity(DataResultObject resultObject, HttpServletRequest request) {

        ResponseEntity responseEntity;

        if (resultObject.getResponseStatus() == ResponseStatus.INTERNAL_SERVER_ERROR) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorMessageResultObject(new Date(), resultObject.getStatus(), ResponseStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Error", request.getRequestURI()));
        } else if (resultObject.getResponseStatus() == ResponseStatus.NOT_FOUND) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorMessageResultObject(new Date(), resultObject.getStatus(), ResponseStatus.NOT_FOUND.getReasonPhrase(), ResponseStatus.NO_DATA.getReasonPhrase(), request.getRequestURI()));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(resultObject);
        }

        return responseEntity;
    }
    protected ResponseEntity<?> prepareResponseEntity(DataResultObjectList resultObject, HttpServletRequest request) {

        ResponseEntity responseEntity;

        if (resultObject.getResponseStatus() == ResponseStatus.INTERNAL_SERVER_ERROR) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorMessageResultObject(new Date(), resultObject.getStatus(), ResponseStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Error", request.getRequestURI()));
        } else if (resultObject.getResponseStatus() == ResponseStatus.NOT_FOUND) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorMessageResultObject(new Date(), resultObject.getStatus(), ResponseStatus.NOT_FOUND.getReasonPhrase(), "Not Found", request.getRequestURI()));
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(resultObject);
        }

        return responseEntity;
    }


}

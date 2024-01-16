package dk.dtu.grp08.customer.presentation;

import lombok.Value;

@Value
public class CustomerFacadeException extends RuntimeException {
    int status;
    Object entity;
}

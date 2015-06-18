package com.novacroft.nemo.tfl.services.transfer;

public class AbstractBase {
    protected ErrorResult errors;

    public ErrorResult getErrors() {
        return errors;
    }

    public void setErrors(ErrorResult errors) {
        this.errors = errors;
    }
}

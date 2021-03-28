package info.esdras.logging.api.exceptions;

import info.esdras.logging.exception.BusinessException;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ApiErrors {

    private List<String> errors;

    public List<String> getErrors() {
        return errors;
    }

    public ApiErrors(BindingResult bindingResult) {
        this.errors = new ArrayList<>();
        bindingResult.getAllErrors().forEach(error -> this.errors.add(error.getDefaultMessage()));
    }

    public ApiErrors(BusinessException businessException) {
        this.errors = Collections.singletonList(businessException.getMessage());
    }
}

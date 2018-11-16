package com.companytest.citydomain.error;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Getter
@ApiModel(description = "Default API error.")
public class ApiError {

    @ApiModelProperty(example = "500", name = "status", required = true)
    private HttpStatus status;
    @ApiModelProperty(example = "internal server error", name = "message", required = true)
    private String message;
    @ApiModelProperty(example = "", name = "error messages detail list", required = true)
    private List<String> errors;

    public ApiError(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }
}

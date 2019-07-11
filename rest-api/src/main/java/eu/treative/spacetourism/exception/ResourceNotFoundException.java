package eu.treative.spacetourism.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue1;
    private final Object fieldValue2;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue1) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue1));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue1 = fieldValue1;
        this.fieldValue2 = null;
    }

}

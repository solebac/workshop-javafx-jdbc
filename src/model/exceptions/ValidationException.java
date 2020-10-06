package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ValidationException(String msg) {
		super(msg);
	}
	
	private Map<String, String> errors = new HashMap<>();
	
	public Map<String, String> getErrors(){
		return errors;
	}
	public void addErrors(String fieldName, String errorMessage) {
		errors.put(fieldName, errorMessage);
	}
}

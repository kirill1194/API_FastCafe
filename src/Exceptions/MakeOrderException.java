package Exceptions;

public class MakeOrderException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message="";
	public MakeOrderException(String message) {
		super();
		this.message = message;
	}
	@Override
	public String getMessage() {
		return message;
	}

}

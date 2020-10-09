package Exceptions;

public class UserAlreadyExsistException extends  RuntimeException {
    String name;

    public UserAlreadyExsistException(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

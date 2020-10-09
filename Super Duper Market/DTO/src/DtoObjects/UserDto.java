package DtoObjects;

public class UserDto {

    private final String role;
    private final String name;

    public UserDto(String role, String name) {
        this.role = role;
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }
}

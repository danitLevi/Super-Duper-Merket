package DtoObjects;

public class StoreBaseDataDto {

    private final int id;
    private final String name;

    public StoreBaseDataDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

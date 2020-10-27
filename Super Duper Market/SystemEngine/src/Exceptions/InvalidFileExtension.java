package Exceptions;

public class InvalidFileExtension extends Exception
{
    private String wantedExtension;
    public InvalidFileExtension(String wantedExtension)
    {
        this.wantedExtension =wantedExtension;
    }

    public String getWantedExtension() {
        return wantedExtension;
    }

    public void setWantedExtension(String wantedExtension) {
        this.wantedExtension = wantedExtension;
    }
}

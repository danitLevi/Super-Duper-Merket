package Exceptions;

public class InvalidFileExtension extends Exception
{
    private String wantedExtention;
    public InvalidFileExtension(String wantedExtesntion)
    {
        wantedExtention=wantedExtesntion;
    }

    public String getWantedExtention() {
        return wantedExtention;
    }

    public void setWantedExtention(String wantedExtention) {
        this.wantedExtention = wantedExtention;
    }
}

public class AmbiguousPersonException extends Exception{
    public AmbiguousPersonException(String fullname)
    {
        super(fullname + " already exists");
    }


}

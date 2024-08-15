package parse;

public class Context {

    public record TimeContext (boolean timePm, boolean timeDoors){}

    public record DateTimeContext (boolean datetimeReverse) {}

}

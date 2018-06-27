import java.util.List;

public interface Populace {
    public int getNewId();

    public Integer addToDB();

    public List listDB();

    public void searchDB(String name);

}

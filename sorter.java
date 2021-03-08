import java.util.List;
public interface Sorter <T extends Comparable>
{
    public static void sort(List<T> list);
}
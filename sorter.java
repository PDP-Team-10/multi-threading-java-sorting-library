import java.util.List;
public interface Sorter <T extends Comparable>
{
    void sort(List<T> list);
}
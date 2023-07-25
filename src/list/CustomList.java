package list;

public interface CustomList<E> extends CustomCollection<E> {


    CustomList<E> subList(int fromIndex, int toIndex);

}

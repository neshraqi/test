package hami.mainapp.hotel;

public interface FilterHotel<T, E> {
    public boolean isMatched(T object, E text);
}

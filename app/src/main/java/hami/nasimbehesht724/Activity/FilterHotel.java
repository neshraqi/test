package hami.nasimbehesht724.Activity;

public interface FilterHotel<T, E> {
    public boolean isMatched(T object, E text);
}

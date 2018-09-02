package com.hami.servicehotel;

public interface FilterHotel<T, E> {
    public boolean isMatched(T object, E text);
}

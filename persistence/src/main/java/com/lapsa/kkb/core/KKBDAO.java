package com.lapsa.kkb.core;

public interface KKBDAO<T, I> {
    T findById(I id);

    T save(T entity) throws KKBPeristenceOperationFailed;
}

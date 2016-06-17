package com.lapsa.kkb.persistence;

public interface KKBDAO<T, I> {
    T findById(I id);

    T save(T entity) throws KKBPeristenceOperationFailed;
}

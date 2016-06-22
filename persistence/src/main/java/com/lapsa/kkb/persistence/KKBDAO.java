package com.lapsa.kkb.persistence;

public interface KKBDAO<T, I> {
    T findById(I id) throws KKBEntityNotFound;

    <Z extends T> Z save(Z entity) throws KKBPeristenceOperationFailed;
}

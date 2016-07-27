package com.lapsa.kkb.persistence;

public interface KKBGeneralDAO<T, I> {
    T findById(I id) throws KKBEntityNotFound, KKBPeristenceOperationFailed;

    T findByIdByPassCache(final I id) throws KKBEntityNotFound, KKBPeristenceOperationFailed;

    void deleteId(I id) throws KKBEntityNotFound, KKBPeristenceOperationFailed;

    <Z extends T> void delete(Z entity) throws KKBPeristenceOperationFailed;

    <Z extends T> Z save(Z entity) throws KKBPeristenceOperationFailed;
}

package com.lapsa.kkb.persistence;

import java.util.List;

import com.lapsa.kkb.core.KKBOrder;

public interface KKBOrderDAO extends KKBGeneralDAO<KKBOrder, String> {
    List<KKBOrder> findAllAuthorizated() throws KKBPeristenceOperationFailed;

    List<KKBOrder> findAllAuthorizatedWithExternalId() throws KKBPeristenceOperationFailed;

    List<KKBOrder> findAllWithoutExternalId() throws KKBPeristenceOperationFailed;
}

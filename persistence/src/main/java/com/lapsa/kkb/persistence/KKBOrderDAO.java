package com.lapsa.kkb.persistence;

import java.util.List;

import com.lapsa.kkb.core.KKBOrder;

public interface KKBOrderDAO extends KKBGeneralDAO<KKBOrder, String> {
    List<KKBOrder> findAllAuthorizated();

    List<KKBOrder> findAllAuthorizatedWithExternalId();

    List<KKBOrder> findAllWithoutExternalId();
}

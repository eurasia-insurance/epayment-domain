package com.lapsa.kkb.dao;

import java.util.List;

import com.lapsa.kkb.core.KKBDocument;
import com.lapsa.kkb.core.KKBOrder;

public interface KKBGeneralDocumentDAO<T extends KKBDocument> extends KKBGeneralDAO<T, Integer> {
    List<T> findByOrder(KKBOrder order) throws KKBPeristenceOperationFailed;
}

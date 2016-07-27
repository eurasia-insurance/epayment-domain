package com.lapsa.kkb.persistence;

import java.util.List;

import com.lapsa.kkb.core.KKBDocument;
import com.lapsa.kkb.core.KKBOrder;

public interface KKBDocumentDAO extends KKBGeneralDAO<KKBDocument, Integer> {
    List<KKBDocument> findByOrder(KKBOrder order) throws KKBPeristenceOperationFailed;
}

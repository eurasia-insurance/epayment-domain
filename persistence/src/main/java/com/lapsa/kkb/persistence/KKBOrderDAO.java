package com.lapsa.kkb.persistence;

import java.util.List;

import com.lapsa.kkb.core.KKBOrder;

public interface KKBOrderDAO extends KKBDAO<KKBOrder, String> {
    List<KKBOrder> findAllAuthorizated();
}

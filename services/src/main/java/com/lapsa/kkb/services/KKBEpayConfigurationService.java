package com.lapsa.kkb.services;

import java.net.URI;

public interface KKBEpayConfigurationService {
    URI getEpayURI();

    String getTemplateName();
}

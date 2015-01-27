package com.tigertext.sdk.entities;

import java.util.List;

/**
 * Created by Zvika on 1/27/15.
 */
public interface User {
    String getToken();

    String getStatus();

    String getFirstName();

    String getLastName();

    String getDisplayName();

    String getUsername();

    String getAvatarUrl();

    List<Email> getEmailAddresses();

    Boolean isDND();

    String getDNDMessage();
}

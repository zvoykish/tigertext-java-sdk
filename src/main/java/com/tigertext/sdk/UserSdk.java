package com.tigertext.sdk;

import com.tigertext.sdk.entities.User;

/**
 * Created by Zvika on 1/28/15.
 */
public interface UserSdk {
    /**
     * Returns the details of the requested user.
     *
     * @param identifier User can be identified by any of the Addressing Schema types
     * @return The user, if such exists, null otherwise
     */
    User get(String identifier);
}

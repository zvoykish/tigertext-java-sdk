package com.tigertext.sdk.entities.impl;

import com.tigertext.sdk.entities.Email;
import com.tigertext.sdk.entities.User;

import java.util.List;

/**
 * Created by Zvika on 1/27/15.
 */
public class UserImpl implements User {
    private final String token;
    private final String status;
    private final String firstName;
    private final String lastName;
    private final String displayName;
    private final String username;
    private final String avatarUrl;
    private final List<Email> emailAddresses;
    private final Boolean dnd;
    private final String dndMessage;

    public UserImpl(String token, String status, String firstName, String lastName, String displayName, String username,
                    String avatarUrl, List<Email> emailAddresses, Boolean dnd, String dndMessage) {
        this.token = token;
        this.status = status;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.emailAddresses = emailAddresses;
        this.dnd = dnd;
        this.dndMessage = dndMessage;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getAvatarUrl() {
        return avatarUrl;
    }

    @Override
    public List<Email> getEmailAddresses() {
        return emailAddresses;
    }

    @Override
    public Boolean isDND() {
        return dnd;
    }

    @Override
    public String getDNDMessage() {
        return dndMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserImpl user = (UserImpl) o;

        if (avatarUrl != null ? !avatarUrl.equals(user.avatarUrl) : user.avatarUrl != null) return false;
        if (displayName != null ? !displayName.equals(user.displayName) : user.displayName != null) return false;
        if (dnd != null ? !dnd.equals(user.dnd) : user.dnd != null) return false;
        if (dndMessage != null ? !dndMessage.equals(user.dndMessage) : user.dndMessage != null) return false;
        if (emailAddresses != null ? !emailAddresses.equals(user.emailAddresses) : user.emailAddresses != null)
            return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (status != null ? !status.equals(user.status) : user.status != null) return false;
        if (token != null ? !token.equals(user.token) : user.token != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = token != null ? token.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
        result = 31 * result + (emailAddresses != null ? emailAddresses.hashCode() : 0);
        result = 31 * result + (dnd != null ? dnd.hashCode() : 0);
        result = 31 * result + (dndMessage != null ? dndMessage.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "USER: { token: " + token +
                ", status: " + status +
                ", firstName: " + firstName +
                ", lastName: " + lastName +
                ", displayName: " + displayName +
                ", username: " + username +
                ", avatarUrl :" + avatarUrl +
                ", emailAddresses: " + emailAddresses +
                ", dnd: " + dnd +
                ", dndMessage: " + dndMessage +
                " }";
    }
}

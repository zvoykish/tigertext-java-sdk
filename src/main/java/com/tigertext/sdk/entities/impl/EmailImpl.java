package com.tigertext.sdk.entities.impl;

import com.tigertext.sdk.entities.Email;

/**
 * Created by Zvika on 1/27/15.
 */
public class EmailImpl implements Email {
    private final String address;
    private final boolean verified;

    public EmailImpl(String address, boolean verified) {
        this.address = address;
        this.verified = verified;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public boolean isVerified() {
        return verified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmailImpl email = (EmailImpl) o;

        if (verified != email.verified) return false;
        if (address != null ? !address.equals(email.address) : email.address != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + (verified ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EmailImpl{" +
                "address='" + address + '\'' +
                ", verified=" + verified +
                '}';
    }
}

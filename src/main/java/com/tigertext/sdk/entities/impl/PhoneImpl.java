package com.tigertext.sdk.entities.impl;

import com.tigertext.sdk.entities.Phone;

/**
 * Created by Zvika on 1/27/15.
 */
public class PhoneImpl implements Phone {
    private String number;
    private boolean verified;

    public PhoneImpl(String number, boolean verified) {
        this.number = number;
        this.verified = verified;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public boolean isVerified() {
        return verified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneImpl phone = (PhoneImpl) o;

        if (verified != phone.verified) return false;
        if (number != null ? !number.equals(phone.number) : phone.number != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (verified ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PhoneImpl{" +
                "number='" + number + '\'' +
                ", verified=" + verified +
                '}';
    }
}

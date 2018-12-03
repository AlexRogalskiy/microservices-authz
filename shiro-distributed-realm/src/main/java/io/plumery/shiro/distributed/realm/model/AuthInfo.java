package io.plumery.shiro.distributed.realm.model;

public class AuthInfo {

    public final String consumerKey;

    public final boolean isPreAuth;

    public AuthInfo(String consumerKey, boolean isPreAuth) {
        this.consumerKey = consumerKey;
        this.isPreAuth = isPreAuth;
    }
}

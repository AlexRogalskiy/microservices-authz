package io.plumery.shiro.distributed.realm.model;

import org.apache.shiro.authc.AuthenticationToken;

public class Consumer implements AuthenticationToken {

    private final AuthInfo authInfo;

    public Consumer(String consumerKey, boolean isPreAuth) {
        this.authInfo = new AuthInfo(consumerKey, isPreAuth);
    }

    @Override
    public AuthInfo getPrincipal() {
        return authInfo;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

}

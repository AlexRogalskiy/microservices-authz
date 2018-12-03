package io.plumery.shiro.distributed.realm;

import io.plumery.shiro.distributed.realm.model.AuthInfo;
import io.plumery.shiro.distributed.realm.model.Consumer;
import io.plumery.shiro.distributed.realm.service.PermissionsService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class ApiRealm extends AuthorizingRealm {

    private final SimpleAccount defaultWildcardPermissions;
    private PermissionsService service;

    public ApiRealm(PermissionsService service) {
        this.service = service;
        this.defaultWildcardPermissions = createWildcardPermissions();

        setCachingEnabled(false);
        setAuthenticationTokenClass(Consumer.class);
        setCredentialsMatcher(new AllowAllCredentialsMatcher());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        AuthInfo authInfo = (AuthInfo) principals.getPrimaryPrincipal();
        if (authInfo.isPreAuth) {
            // -- subject is pre-authenticated - fetch and populate the permissions
            return populateUserPermissions(authInfo);
        }
        /*
         Return trusted wildcard permissions account for
         internal service-to-service communication bypassing Authentication Gateway
        */
        return defaultWildcardPermissions;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        AuthInfo authInfo = ((Consumer) token).getPrincipal();
        if (authInfo.isPreAuth) {
            // -- subject is pre-authenticated - fetch and populate the permissions
            return populateUserPermissions(authInfo);
        }
        /*
         Return trusted wildcard permissions account for
         internal service-to-service communication bypassing Authentication Gateway
        */
        return defaultWildcardPermissions;
    }

    private SimpleAccount populateUserPermissions(AuthInfo authInfo) {
        SimpleAccount account = new SimpleAccount(authInfo, null, getName());
        account.addStringPermissions(service.getUserPermissions(authInfo.consumerKey));
        return account;
    }

    private SimpleAccount createWildcardPermissions() {
        SimpleAccount account = new SimpleAccount(new AuthInfo("", false), null, getName());
        account.addObjectPermission(new WildcardPermission("*"));
        return account;
    }
}

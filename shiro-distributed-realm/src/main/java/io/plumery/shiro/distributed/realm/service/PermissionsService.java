package io.plumery.shiro.distributed.realm.service;

import io.plumery.shiro.distributed.realm.model.Permissions;

public interface PermissionsService {

    Permissions getUserPermissions(String userId);

}

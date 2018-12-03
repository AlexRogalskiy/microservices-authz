package io.plumery.shiro.distributed.redis;

import io.plumery.shiro.distributed.realm.model.Permissions;
import io.plumery.shiro.distributed.realm.service.PermissionsService;

public class PermissionsServiceImpl implements PermissionsService {
    public Permissions getUserPermissions(String userId) {
        Permissions permissions = new Permissions();
        permissions.add("urn:inventoryitems:list");
        permissions.add("urn:inventoryitems:create");

        return permissions;
    }
}

package io.plumery.shiro.distributed.realm.service;

import io.plumery.shiro.distributed.realm.model.Permissions;

public class PermissionsServiceImpl implements PermissionsService {
    public Permissions getUserPermissions(String userId) {
        Permissions permissions = new Permissions();
        permissions.add("urn:inventoryitems:list");
        permissions.add("urn:inventoryitems:create");

        return permissions;
    }
}

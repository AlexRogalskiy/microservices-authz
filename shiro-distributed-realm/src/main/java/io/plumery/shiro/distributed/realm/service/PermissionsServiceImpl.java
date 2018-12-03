package io.plumery.shiro.distributed.realm.service;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import io.plumery.shiro.distributed.realm.model.Permissions;
import okhttp3.*;

public class PermissionsServiceImpl implements PermissionsService {
    private static final Moshi MOSHI = new Moshi.Builder().build();

    private OkHttpClient httpClient;
    private static final JsonAdapter<Permissions> PERMISSIONS_JSON_ADAPTER = MOSHI.adapter(Permissions.class);

    public PermissionsServiceImpl(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Permissions getUserPermissions(String userId) {
        Permissions permissions = new Permissions();
        permissions.add("urn:inventoryitems:list");
        permissions.add("urn:inventoryitems:create");

        return permissions;
    }



}

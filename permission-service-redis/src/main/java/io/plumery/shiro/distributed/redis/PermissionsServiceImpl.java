package io.plumery.shiro.distributed.redis;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import io.plumery.shiro.distributed.realm.model.Permissions;
import io.plumery.shiro.distributed.realm.service.PermissionsService;
import redis.clients.jedis.Jedis;

import java.io.IOException;

public class PermissionsServiceImpl implements PermissionsService {
    private final JsonAdapter<Permissions> PERMISSION_JSON_ADAPTER;
    private final Moshi moshi;
    private final Jedis jedis;

    public PermissionsServiceImpl(Jedis jedis) {
        this.jedis = jedis;
        this.moshi = new Moshi.Builder().build();
        PERMISSION_JSON_ADAPTER = moshi.adapter(Permissions.class);
    }

    public Permissions getUserPermissions(String userId) {
        String permissionsJson = jedis.get(userId);

        Permissions permissions;
        if (permissionsJson != null && permissionsJson.length() > 0) {
            try {
                permissions = PERMISSION_JSON_ADAPTER.fromJson(permissionsJson);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            permissions = new Permissions();
        }

        return permissions;
    }
}

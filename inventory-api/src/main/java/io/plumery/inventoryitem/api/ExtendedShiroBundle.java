package io.plumery.inventoryitem.api;

import com.bendb.dropwizard.redis.JedisBundle;
import com.bendb.dropwizard.redis.JedisFactory;
import io.dropwizard.setup.Environment;
import io.plumery.shiro.distributed.realm.ApiRealm;
import io.plumery.shiro.distributed.redis.PermissionsServiceImpl;
import org.apache.shiro.realm.Realm;
import org.secnod.dropwizard.shiro.ShiroBundle;
import org.secnod.dropwizard.shiro.ShiroConfiguration;
import redis.clients.jedis.Jedis;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class ExtendedShiroBundle extends ShiroBundle<InventoryItemApiConfiguration> {
    private Jedis jedis;

    @Override
    public void run(InventoryItemApiConfiguration configuration, Environment environment) {
        // glue together Jedis and Shiro bundles
        JedisBundle bundle = constructJedisBundle(configuration, environment);

        jedis = bundle.getPool().getResource();

        populateDummyData(jedis);

        super.run(configuration, environment);
    }

    private void populateDummyData(Jedis jedis) {
        InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream("dummy-permissions.json");
        Scanner s = new Scanner(in).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        jedis.append("user", result);
    }

    private JedisBundle constructJedisBundle(InventoryItemApiConfiguration configuration, Environment environment) {
        JedisBundle bundle = new JedisBundle<InventoryItemApiConfiguration>() {
            @Override
            public JedisFactory getJedisFactory(InventoryItemApiConfiguration inventoryItemApiConfiguration) {
                return configuration.getJedisFactory();
            }
        };

        try {
            bundle.run(configuration, environment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bundle;
    }

    @Override
    protected ShiroConfiguration narrow(InventoryItemApiConfiguration configuration) {
        return configuration.shiro;
    }

    @Override
    protected Collection<Realm> createRealms(InventoryItemApiConfiguration configuration) {
        Realm r = new ApiRealm(new PermissionsServiceImpl(jedis));
        return Collections.singleton(r);
    }
}

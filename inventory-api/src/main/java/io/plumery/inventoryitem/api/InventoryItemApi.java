package io.plumery.inventoryitem.api;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.plumery.inventoryitem.api.resources.InventoryItemResource;
import io.plumery.shiro.distributed.realm.ApiRealm;
import io.plumery.shiro.distributed.redis.PermissionsServiceImpl;
import org.apache.shiro.realm.Realm;
import org.secnod.dropwizard.shiro.ShiroBundle;
import org.secnod.dropwizard.shiro.ShiroConfiguration;
import org.secnod.shiro.jaxrs.ShiroExceptionMapper;

import java.util.Collection;
import java.util.Collections;


public class InventoryItemApi extends Application<InventoryItemApiConfiguration> {
    private final ShiroBundle<InventoryItemApiConfiguration> shiro = new ShiroBundle<InventoryItemApiConfiguration>() {
        @Override
        protected ShiroConfiguration narrow(InventoryItemApiConfiguration configuration) {
            return configuration.shiro;
        }

        @Override
        protected Collection<Realm> createRealms(InventoryItemApiConfiguration configuration) {
            Realm r = new ApiRealm(new PermissionsServiceImpl());
            return Collections.singleton(r);
        }
    };

    public static void main(String[] args) throws Exception {
        new InventoryItemApi().run(args);
    }

    @Override
    public void run(InventoryItemApiConfiguration configuration, Environment environment) {
        environment.jersey().register(new InventoryItemResource());
        environment.jersey().register(new ShiroExceptionMapper());
    }

    @Override
    public void initialize(Bootstrap<InventoryItemApiConfiguration> bootstrap) {
        bootstrap.addBundle(shiro);
    }
}

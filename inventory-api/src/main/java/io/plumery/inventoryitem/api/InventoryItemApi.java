package io.plumery.inventoryitem.api;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.plumery.inventoryitem.api.resources.InventoryItemResource;
import org.secnod.shiro.jaxrs.ShiroExceptionMapper;


public class InventoryItemApi extends Application<InventoryItemApiConfiguration> {
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
        bootstrap.addBundle(new ExtendedShiroBundle());
    }
}

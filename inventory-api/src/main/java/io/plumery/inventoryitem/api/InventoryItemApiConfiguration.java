package io.plumery.inventoryitem.api;

import io.dropwizard.Configuration;
import org.secnod.dropwizard.shiro.ShiroConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class InventoryItemApiConfiguration extends Configuration {
    @Valid
    @NotNull
    public ShiroConfiguration shiro;
}

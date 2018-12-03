package io.plumery.inventoryitem.api;

import com.bendb.dropwizard.redis.JedisFactory;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.secnod.dropwizard.shiro.ShiroConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class InventoryItemApiConfiguration extends Configuration {
    @Valid
    @NotNull
    public ShiroConfiguration shiro;

    @NotNull
    @JsonProperty
    private JedisFactory redis;

    public JedisFactory getJedisFactory() {
        return redis;
    }

    public void setJedisFactory(JedisFactory jedisFactory) {
        this.redis = jedisFactory;
    }
}

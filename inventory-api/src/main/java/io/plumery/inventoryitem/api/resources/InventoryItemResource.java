package io.plumery.inventoryitem.api.resources;

import io.plumery.inventoryitem.api.core.InventoryItem;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;


@Path("/inventory-items")
@Produces(MediaType.APPLICATION_JSON)
public class InventoryItemResource {
    private Collection<InventoryItem> inventoryItems = new ArrayList<>();

    public InventoryItemResource() {
    }

    @GET
    @RequiresPermissions("urn:inventoryitems:list")
    public Collection<InventoryItem> inventoryItems() {
        System.out.println(inventoryItems.size());

        return inventoryItems;
    }

    @POST
    @RequiresPermissions("urn:inventoryitems:create")
    public Response create(InventoryItem inventoryItem) {
        inventoryItems.add(inventoryItem);

        return Response.status(202).build();
    }
}

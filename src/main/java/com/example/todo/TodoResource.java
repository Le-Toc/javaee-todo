package com.example.todo;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoResource {

    @Inject
    private TodoService service;

    @GET
    public List<Todo> list() { return service.list(); }

    @POST
    public Response create(Todo t) {
        if (t.getTitle() == null || t.getTitle().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("title is required").build();
        }
        return Response.status(Response.Status.CREATED).entity(service.create(t)).build();
    }

    @POST
    @Path("{id}/toggle")
    public Response toggle(@PathParam("id") long id) {
        Todo t = service.toggle(id);
        if (t == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(t).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id) {
        boolean ok = service.delete(id);
        return ok ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}

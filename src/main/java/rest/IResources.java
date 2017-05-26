package rest;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by vpnk on 03.05.17.
 */
public interface IResources<E> {

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") int id);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<E> getAll();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(E element);

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, E element);

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id")int id);

}

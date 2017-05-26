package rest;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContexts;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.org.apache.regexp.internal.RE;
import domain.*;

import java.util.List;

/**
 * Created by pwilk on 26.05.17.
 */
@Path("/product")
@Stateless
public class ProductResources implements IResources<Product> {

    @PersistenceContext
    EntityManager em;

    @Override
    public Response get(int id) {
        Product result = em.createNamedQuery("product.id", Product.class)
                .setParameter("productId", id)
                .getSingleResult();
        if(result == null)
            return Response.status(404).build();
        return Response.ok(result).build();
    }

    @Override
    public List<Product> getAll() {
        return em.createNamedQuery("product.all", Product.class).getResultList();
    }

    @Override
    public Response add(Product element) {
        em.persist(element);
        Response.ok(element.getId()).build();
        return null;
    }

    @Override
    public Response update(int id, Product element) {
        Product result = em.createNamedQuery("product.id", Product.class)
                .setParameter("productId", id)
                .getSingleResult();
        if (result == null)
            return Response.status(404).build();
        result.setPrice(element.getPrice());
        result.setProductName(element.getProductName());
        result.setProductCategory(element.getProductCategory());
        em.persist(result);
        return Response.ok().build();
    }

    @Override
    public Response delete(int id) {
        Product result = em.createNamedQuery("product.id",Product.class)
                .setParameter("productId", id)
                .getSingleResult();
        if (result == null)
            return Response.status(404).build();
        em.remove(result);
        return Response.ok().build();
    }

    @POST
    @Path("/{id}/comments")
    public Response addComment(@PathParam("id")int id, Comment comment){
        Product result = em.createNamedQuery("product.id", Product.class)
                .setParameter("productId", id)
                .getSingleResult();
        if(result == null)
            return Response.status(404).build();
        result.addComment(comment);
        em.persist(result);
        return Response.ok().build();
    }
    @GET
    @Path("/{id}/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> showComments(@PathParam("id")int id, Comment comment){
        Product result = em.createNamedQuery("product.id",Product.class)
                .setParameter("productId", id)
                .getSingleResult();

        if (result == null)
            return null;
        return result.getComments();
    }
    @DELETE
    @Path("/{id}/comments/{commentId}")
    public Response removeComment(@PathParam("id")int id, @PathParam("commentId")int commentId){
        Product result = em.createNamedQuery("product.id",Product.class)
                .setParameter("productId", id)
                .getSingleResult();
        if (result == null)
            return Response.status(404).build();
        if (result.getComments().get(commentId) == null)
            return Response.status(404).build();
        result.getComments().remove(commentId);
        return Response.ok().build();
    }
    @GET
    @Path("/bycategory/{category}")
    public List<Product> byCategory(@PathParam("category") Category category){
        return em.createNamedQuery("product.category", Product.class)
                .setParameter("productCategory", category)
                .getResultList();
    }
    @GET
    @Path("/byprice/{min}/{max}")
    public List<Product> byPrice(@PathParam("min")int minimal, @PathParam("max") int maximum){
        return em.createNamedQuery("product.price", Product.class)
                .setParameter("minPrice",minimal)
                .setParameter("maxPrice",maximum)
                .getResultList();
    }
    @GET
    @Path("/byname/{name}")
    public List<Product> byName(@PathParam("name")String name){
        return em.createNamedQuery("product.name", Product.class)
                .setParameter("productName", name)
                .getResultList();
    }
}

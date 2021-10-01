/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geoloc.aws;

import com.geoloc.beans.Cyclist;
import com.geoloc.models.CyclistModel;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.bson.types.ObjectId;

/**
 *
 * @author Lucas
 */
@Path("Cyclist")

public class CyclistREST {

    @EJB
    private CyclistModel cyclistModel;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String test() {
        return "I'm in the api rest";
    }

    @GET
    @Path("/getList")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cyclist> read() {
        return cyclistModel.read();
    }

    @POST
    @Path("/addCyclist")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Cyclist cyclist) {
        cyclistModel.create(cyclist);
        Response response = Response.status(Response.Status.OK).build();
        return response;
    }

    @DELETE
    @Path("/deleteCyclist/{_id}")
    public Response delete(@PathParam("_id") String _id) {
        try {
            ObjectId id = new ObjectId(_id);
            Cyclist cyclist = new Cyclist();
            cyclist.setId(id);
            cyclistModel.delete(cyclist);
        }catch(Exception e){
            System.out.println(e);
        }

        Response response = Response.status(Response.Status.OK).build();
        return response;
    }
    
    @PUT
    @Path("/updateCyclist/{_id}")
     @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Cyclist cyclist, @PathParam("_id") String _id){
        
        cyclist.setId(new ObjectId(_id));
        cyclistModel.update(cyclist);
        
        Response response = Response.status(Response.Status.OK).build();
        return response;
    }
    
}

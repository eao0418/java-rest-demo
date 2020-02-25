package eao0418.demo.rest.services;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.POST;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import eao0418.demo.rest.tools.RestUtil;
import eao0418.demo.rest.repository.UidRepository;
import eao0418.demo.rest.resources.Uid;

@Path("v1/uid")
public class UidService {

    private static final Logger LOG = LogManager.getLogger(UidService.class);

    /**
     * Assigns a new UID to a user.
     * 
     * @param userId The userId to assign the new UID to
     * @return A Response object
     */
    @POST
    @Path("assignment/{userName}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response setUid(@PathParam("userName") String userName) {

        LOG.debug("setUid: Entered method");

        UidRepository repo = null;
        try {

            repo = new UidRepository();

        } catch (Exception e) {

            LOG.error("A repository instance could not be obtained");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        Uid uid = null;

        // Check to make sure the username is our pre-defined user pattern;

        if (!RestUtil.isUserValidPattern(userName)) {

            LOG.error("setUid: the value provided for user input is not a valid pattern: [" + String.valueOf(userName)
                    + "]");

            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // Check to make sure the user does not have a current assignment

        uid = repo.readObject(userName);

        if (null != uid) {

            return Response.status(Response.Status.CONFLICT).build();

        } else {

            uid = repo.createObject(userName);
        }

        LOG.debug("setUid: Returning data");

        return Response.status(Response.Status.CREATED).entity(uid).build();
    }

    /**
     * Gets the currently assigned Uid data for the user.
     * 
     * @param userName The user to look up.
     * @return A Response object.
     */
    @GET
    @Path("assignment/{userName}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getUidAssignment(@PathParam("userName") String userName) {

        LOG.debug("getUidAssignment: Entered method");

        UidRepository repo = null;
        try {

            repo = new UidRepository();

        } catch (Exception e) {

            LOG.error("A repository instance could not be obtained");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

        }

        Uid uid = null;

        // Check to make sure the username is our pre-defined user pattern;

        if (!RestUtil.isUserValidPattern(userName)) {

            LOG.error("getUidAssignment: the value provided for user input is not a valid pattern: ["
                    + String.valueOf(userName) + "]");

            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        uid = repo.readObject(userName);

        if (null == uid) {

            return Response.status(Response.Status.NOT_FOUND).build();

        } else {

            return Response.status(Response.Status.OK).entity(uid).build();
        }

    }

    /**
     * Gets all currently assigned Uid objects.
     * 
     * @return a Response object.
     */
    @GET
    @Path("assignment/assignments")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getAllUidAssignments() {

        LOG.debug("getAllUidAssignments: Entered method");

        UidRepository repo = null;
        try {

            repo = new UidRepository();

        } catch (Exception e) {

            LOG.error("A repository instance could not be obtained");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

        }

        List<Uid> assignmentList = new ArrayList<>();

        assignmentList = repo.readAllObjects();

        if (assignmentList.isEmpty()) {

            return Response.status(Response.Status.NOT_FOUND).build();

        } else {

            return Response.status(Response.Status.OK).entity(assignmentList).build();
        }

    }

    /**
     * Updates the uid assignment for the user.
     * 
     * @param userName The userName to update.
     * @return A Response object.
     */
    @PUT
    @Path("assignment/{userName}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response updateUidAssignment(@PathParam("userName") String userName) {

        LOG.debug("updateUidAssignment: Entered method");

        UidRepository repo = null;
        try {

            repo = new UidRepository();

        } catch (Exception e) {

            LOG.error("A repository instance could not be obtained");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

        }

        Uid uid = null;

        // Check to make sure the username is our pre-defined user pattern;
        // Verify the user exists before proceeding.

        LOG.debug("updateUidAssignment: Exiting method");

        if (this.checkUser(userName)) {

            boolean isSuccess = false;

            isSuccess = repo.updateObject(userName);

            if (isSuccess) {

                uid = repo.readObject(userName);

                return Response.status(Response.Status.OK).entity(uid).build();

            } else {

                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }

        } else {

            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    /**
     * Deletes the assignment entry.
     * 
     * @param userName The user to delete the assignment for.
     * @return A Response object.
     */
    @DELETE
    @Path("assignment/{userName}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response deleteUidAssignment(@PathParam("userName") String userName) {

        LOG.debug("updateUidAssignment: Entered method");

        UidRepository repo = null;
        try {

            repo = new UidRepository();

        } catch (Exception e) {

            LOG.error("A repository instance could not be obtained");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

        }
        // Check to make sure the username is our pre-defined user pattern;
        // Check to make sure the username is in the database

        if (this.checkUser(userName)) {

            LOG.debug("updateUidAssignment: Exiting method");

            boolean isSuccess = false;

            isSuccess = repo.deleteObject(userName);

            if (isSuccess) {

                return Response.status(Response.Status.NO_CONTENT).build();

            } else {

                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }

        } else {

            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * Validates if a userName pattern is valid and if the user exists in the db
     * 
     * @param userName The user to check.
     * @return True if good to proceed, false if not.
     */
    private boolean checkUser(String userName) {

        boolean isValid = false;

        UidRepository repo = null;
        try {

            repo = new UidRepository();

        } catch (Exception e) {

            LOG.error("A repository instance could not be obtained");

            return isValid;
        }

        if (!RestUtil.isUserValidPattern(userName)) {

            LOG.error("updateUidAssignment: the value provided for user input is not a valid pattern: ["
                    + String.valueOf(userName) + "]");
        } else {

            // Verify the user exists
            Uid uid = null;
            uid = repo.readObject(userName);

            if (null != uid) {

                isValid = true;
            }

        }

        return isValid;
    }

}
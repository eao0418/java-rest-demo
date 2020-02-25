package eao0418.demo.rest.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

import java.time.Instant;

import com.mysql.cj.jdbc.MysqlDataSource;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import eao0418.demo.rest.resources.Uid;
import eao0418.demo.rest.repository.DataSourceManager;

public class UidRepository {

    private static MysqlDataSource ds = null;
    private static Logger LOG = LogManager.getLogger(UidRepository.class);

    public UidRepository() throws Exception {

        ds = DataSourceManager.getDataSourceInstance();
    }

    /**
     * Gets One uid assignment from the database.
     * 
     * @param userName The username to get the assignment for.
     * @return A Uid object.
     */
    public Uid readObject(String userName) {

        LOG.debug("readObject: Started method");
        Uid uid = null;

        PreparedStatement statement = null;
        String query = "SELECT * FROM rest_app.uid WHERE user_id = ?";

        try {
            Connection con = ds.getConnection();
            statement = con.prepareStatement(query);
            statement.setString(1, userName);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                uid = new Uid(resultSet.getString(1), resultSet.getInt(2));
                uid.setAssignTime(resultSet.getLong(3));
                uid.setModifiedTime(resultSet.getLong(4));
            }
            con.close();

        } catch (Exception e) {

            LOG.error("An exception was caught when updating the object", e);

        } finally {
            if (null != statement) {
                try {
                    statement.close();
                } catch (SQLException e) {

                    LOG.error("Error closing statement", e);
                }
            }
        }

        LOG.debug("readObject: Ended method");

        return uid;
    }

    public List<Uid> readAllObjects() {

        LOG.debug("readAllObjects: Entered method.");

        List<Uid> outputList = new ArrayList<>();

        PreparedStatement statement = null;
        String query = "SELECT * FROM rest_app.uid";

        try {
            Connection con = ds.getConnection();
            statement = con.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.isBeforeFirst()) {

                while (resultSet.next()) {

                    Uid uid = new Uid(resultSet.getString(1), resultSet.getInt(2));
                    uid.setAssignTime(resultSet.getLong(3));
                    uid.setModifiedTime(resultSet.getLong(4));

                    outputList.add(uid);

                }
            }
            con.close();

        } catch (Exception e) {

            LOG.error("An exception was caught when updating the object", e);

        } finally {
            if (null != statement) {
                try {
                    statement.close();
                } catch (SQLException e) {

                    LOG.error("Error closing statement", e);
                }
            }
        }

        LOG.debug("readAllObjects: Leaving method with output size [" + outputList.size() + "].");

        return outputList;
    }

    /**
     * Creates one uid assignment in the database.
     * 
     * @param userName The username to create the assignment for.
     * @return A Uid object.
     */
    public Uid createObject(String userName) {

        LOG.debug("readObject: Started method");

        int affectedRows = 0;
        int uidAssignment = 0;
        Uid uid = null;

        uidAssignment = this.getNextUid();

        if (uidAssignment == 0) {

            return uid;
        }

        Instant now = Instant.now();
        Long assignTime = now.toEpochMilli();

        PreparedStatement statement = null;
        String insertStatement = "INSERT INTO rest_app.uid VALUES(?, ?, ?, null);";

        try {
            Connection con = ds.getConnection();
            statement = con.prepareStatement(insertStatement);
            statement.setString(1, userName);
            statement.setInt(2, uidAssignment);
            statement.setLong(3, assignTime);

            affectedRows = statement.executeUpdate();

            con.close();

        } catch (Exception e) {

            LOG.error("Caught an exception creating the UID assignment", e);

        } finally {
            if (null != statement) {
                try {
                    statement.close();
                } catch (SQLException e) {

                    LOG.error("Error closing statement", e);
                }
            }
        }

        if (affectedRows > 0) {

            uid = readObject(userName);
        }

        LOG.debug("readObject: Ended method");

        return uid;
    }

    /**
     * Updates the Uid assignment for a user.
     * 
     * @param userName The username to update the assignment for.
     * @return True if succesful, false if unsuccessful.
     */
    public boolean updateObject(String userName) {

        LOG.debug("updateObject: entered method.");

        int uidAssignment = 0;
        int affectedRows = 0;
        boolean isSuccessful = false;

        uidAssignment = this.getNextUid();

        if (uidAssignment == 0) {

            return isSuccessful;
        }

        Instant now = Instant.now();
        Long updateTime = now.toEpochMilli();

        PreparedStatement statement = null;
        // String updateStatement = new StringBuilder().append("UPDATE rest_app.uid
        // ").append("SET uid_number = ?, ")
        // .append("SET modify_time = ? ").append("WHERE user_id = ?").toString();
        String updateStatement = "UPDATE rest_app.uid SET uid_number = ?, modify_time = ? WHERE user_id = ?";

        try {

            Connection con = ds.getConnection();
            statement = con.prepareStatement(updateStatement);
            statement.setInt(1, uidAssignment);
            statement.setLong(2, updateTime);
            statement.setString(3, userName);

            affectedRows = statement.executeUpdate();

            con.close();

        } catch (Exception e) {

            LOG.error("Caught an exception upating the user record.", e);
        } finally {
            if (null != statement) {
                try {
                    statement.close();
                } catch (SQLException e) {

                    LOG.error("Error closing statement", e);
                }
            }
        }
        if (affectedRows > 0) {

            isSuccessful = true;
        }

        LOG.debug("readObject: Ended method");

        return isSuccessful;
    }

    /**
     * Deletes the Uid assignment for the specified user.
     * 
     * @param userName The username to delete the assignment for.
     * @return True if succesful, false if unsuccessful.
     */
    public boolean deleteObject(String userName) {

        LOG.debug("deleteObject: entered method.");

        int affectedRows = 0;
        boolean isSuccessful = false;

        PreparedStatement statement = null;
        String deleteStatement = new StringBuilder().append("DELETE FROM rest_app.uid ").append("WHERE user_id = ?")
                .toString();

        try {
            Connection con = ds.getConnection();
            statement = con.prepareStatement(deleteStatement);
            statement.setString(1, userName);

            affectedRows = statement.executeUpdate();

            con.close();

        } catch (Exception e) {

            LOG.error("deleteObject: Caught an exception deleting the object", e);

        } finally {
            if (null != statement) {
                try {
                    statement.close();
                } catch (SQLException e) {

                    LOG.error("deleteObject: Error closing statement", e);
                }
            }
        }
        if (affectedRows > 0) {

            isSuccessful = true;
        }

        LOG.debug("deleteObject: Ended method");

        return isSuccessful;
    }

    /**
     * Gets the next Uid to be assigned.
     * 
     * @return The next Uid number.
     */
    private int getNextUid() {

        LOG.debug("getNextUid: Started method");
        int assignment = 0;

        PreparedStatement statement = null;
        String query = "SELECT MAX(uid_number) FROM rest_app.uid";

        try {
            Connection con = ds.getConnection();
            statement = con.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                assignment = resultSet.getInt(1);

                if (assignment == 0) {

                    assignment += 501;
                } else {

                    assignment += 1;
                }
            }
            con.close();

        } catch (Exception e) {

            LOG.error("getNextUid: Caught an exception executing the query", e);

        } finally {
            if (null != statement) {
                try {
                    statement.close();
                } catch (SQLException e) {

                    LOG.error("getNextUid: Error closing statement", e);
                }
            }
        }

        LOG.debug("getNextUid: Ended method");

        return assignment;

    }
}
package eao0418.demo.rest.repository;

import java.util.Properties;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class DataSourceConfigManager {

    private static final Logger LOG = LogManager.getLogger(DataSourceConfigManager.class);
    @Context
    private static ServletContext ctx;

    public DataSourceConfigManager() {

    }

    /**
     * Looks in the WEB-INF/classes directory for a database config file.
     * Gets the connection properties from the file.
     * @return The database connection properties.
     */
    public Properties getDataSourceProperties() {

        LOG.debug("getDataSourceProperties: Entered method");

        Properties properties = new Properties();
        
        try (InputStream inStream =  this.getClass().getResourceAsStream("/dbconfig.properties")) {
            
            properties.load(inStream);

        } catch (Exception e) {
            
            LOG.error("Could not get InputStream to datatabase config file", e);
        }

        LOG.debug("getDataSourceProperties: Exited method");

        return properties;
    }
}
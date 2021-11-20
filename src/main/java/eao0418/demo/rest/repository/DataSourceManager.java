package eao0418.demo.rest.repository;

import java.util.Properties;

import com.mysql.cj.jdbc.MysqlDataSource;

import eao0418.demo.rest.repository.DataSourceConfigManager;

class DataSourceManager {

    private static MysqlDataSource dataSource = null;

    private DataSourceManager() {}

    /**
     * Gets an instance of the MysqlDataSource
     * @return A MysqlDataSource object.
     */
    public static MysqlDataSource getDataSourceInstance() throws RuntimeException {

        if (null == dataSource) {

            Properties dataSourceProps = new Properties();

            DataSourceConfigManager mgr = new DataSourceConfigManager();

            dataSourceProps = mgr.getDataSourceProperties();

            if (null != dataSourceProps && !dataSourceProps.isEmpty()) {
            
                dataSource = new MysqlDataSource();
                dataSource.setUser(dataSourceProps.getProperty("db.user"));
                dataSource.setPassword(dataSourceProps.getProperty("db.password"));
                dataSource.setServerName(dataSourceProps.getProperty("db.servername"));
                dataSource.setPort(Integer.parseInt(dataSourceProps.getProperty("db.port")));
                dataSource.setDatabaseName(dataSourceProps.getProperty("db.databasename"));

            } else {

                throw new RuntimeException("The database properties file could not be reached!");
            }

            return dataSource;
        } else {

            return dataSource;
        }
    }


}
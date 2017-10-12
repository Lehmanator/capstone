package api;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

import org.springframework.jdbc.support.JdbcUtils;

public class DBHandler {
    private static final String BUCKET_NAME = "capitalfun-images";
    private static final String USERS_TABLE = "users";
    private static final String LOGOS_TABLE = "logos";
    private static final String INSERT = "INSERT INTO %s (%s) VALUES (%s);";
    private static final String SELECT = "SELECT %s FROM %s;";
    private static final String SELECT_WHERE = "SELECT %s FROM %s WHERE %s;";
    private static final String UPDATE = "UPDATE %s SET %s WHERE %s;";
    private static final String BASE_URL =
            "https://s3.us-east-2.amazonaws.com/capitalfun-images/%s";

    private Connection connection;
    private AmazonS3 awsClient;

    public DBHandler(Connection connection, AmazonS3 awsClient) {
        this.connection = connection;
        this.awsClient = awsClient;
    }

    public boolean executeQuery(String query) throws SQLException {
        return connection.prepareStatement(query).execute();
    }

    public List<Map<String, Object>> executeQueryWithResults(String query) throws SQLException {
        List<Map<String, Object>> queryResults = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet results = statement.executeQuery();
        ResultSetMetaData rsmd = results.getMetaData();
        int cols = rsmd.getColumnCount();

        Boolean go = results.first();
        while (go) {
            Map<String, Object> currentObject = new HashMap<>();
            for (int i = 1; i <= cols; i++) {
                String column = JdbcUtils.lookupColumnName(rsmd, i);
                Object value = results.getObject(column);
                currentObject.put(column, value);
            }
            go = results.next();
            queryResults.add(currentObject);
        }
        return queryResults;
    }

    public String createSelectUsersTableQuery(List<String> params, String where, String val) {
        if (where == null || val == null) {
            return createSelectQuery(params, USERS_TABLE);
        }
        return createSelectWhereQuery(params, where, val, USERS_TABLE);
    }

    public String createSelectLogosTableQuery(List<String> params, String where, String val) {
        if (where == null || val == null) {
            return createSelectQuery(params, LOGOS_TABLE);
        }
        return createSelectWhereQuery(params, where, val, LOGOS_TABLE);
    }

    public String createInsertUsersTableQuery(Map<String, String> params) {
        return createInsertQuery(params, USERS_TABLE);
    }

    public String createInsertLogosTableQuery(Map<String, String> params) {
        return createInsertQuery(params, LOGOS_TABLE);
    }

    public String createUpdateUsersTableQuery(
            Map<String, String> params, String where, String val) {
        return createUpdateQuery(params, USERS_TABLE, where, val);
    }

    public String createUpdateLogosTableQuery(
            Map<String, String> params, String where, String val) {
        return createUpdateQuery(params, LOGOS_TABLE, where, val);
    }

    public void uploadImage(
            String userName,
            String name,
            InputStream stream,
            ObjectMetadata metadata) throws AmazonS3Exception {
        String fileName = createFileName(userName, name);
//        InitiateMultipartUploadResult result = awsClient.initiateMultipartUpload(new InitiateMultipartUploadRequest(BUCKET_NAME, fileName, metadata));
//        UploadPartResult result1 = awsClient.uploadPart(new UploadPartRequest().withUploadId(result.getUploadId()).withInputStream(stream));
//        CompleteMultipartUploadResult finalResult = awsClient.completeMultipartUpload(new CompleteMultipartUploadRequest(BUCKET_NAME, name, result.getUploadId(), Arrays.asList(result1.getPartETag())));
        awsClient.putObject(BUCKET_NAME, fileName, stream, metadata);
//        awsClient.setObjectAcl(BUCKET_NAME, fileName, CannedAccessControlList.PublicRead);
//        return finalResult.getLocation();
    }

    public ObjectMetadata getImage(String userName, String name) throws AmazonS3Exception {
        S3Object object = awsClient.getObject(BUCKET_NAME, createFileName(userName, name));
        return object.getObjectMetadata();
    }

    public String getImageUrl(String userName, String name) {
        return String.format(BASE_URL, createFileName(userName, name));
    }

    private String createFileName(String userName, String name) {
        String fileParams[] = name.split("\\.");
        return Integer.toString((userName + fileParams[0]).hashCode()) + "." + fileParams[1];
    }

    private String createSelectQuery(List<String> params, String table) {
        StringBuilder fields = new StringBuilder();
        for (String param : params) {
            String current = param + ",";
            fields.append(current);
        }
        fields.deleteCharAt(fields.length() - 1);
        return String.format(SELECT, fields.toString(), table);
    }

    private String createSelectWhereQuery(List<String> params, String where, String val, String table) {
        StringBuilder fields = new StringBuilder();
        for (String param : params) {
            String current = param + ",";
            fields.append(current);
        }
        fields.deleteCharAt(fields.length() - 1);
        String constraint = where + "=" + val;
        return String.format(SELECT_WHERE, fields.toString(), table, constraint);
    }

    private String createInsertQuery(Map<String, String> params, String table) {
        StringBuilder fields = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (String key : params.keySet()) {
            String field = key + ",";
            String value = "\"" + params.get(key) + "\",";
            fields.append(field);
            values.append(value);
        }

        fields.deleteCharAt(fields.length() - 1);
        values.deleteCharAt(values.length() - 1);

        return String.format(INSERT, table, fields.toString(), values.toString());
    }

    private String createUpdateQuery(
            Map<String, String> params, String table, String where, String val) {
        StringBuilder setValues = new StringBuilder();
        for (String key : params.keySet()) {
            String current = key + "=" + params.get(key) + ",";
            setValues.append(current);
        }
        setValues.deleteCharAt(setValues.length() - 1);
        String constraint = where + "=" + val;
        return String.format(UPDATE, table, setValues.toString(), constraint);
    }
}

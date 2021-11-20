package eao0418.demo.rest.resources;

public class Uid {

    private String userName;
    private int uid;
    private Long assignTime = 0L;
    private Long modifiedTime = 0L;

    public Uid () {}

    public Uid (String userName, int uid) {

        this.userName = userName;
        this.uid = uid;
    }
    public void setUserName (String userName) throws IllegalArgumentException {

        this.userName = userName;
    }
    public void setUid (int id) throws IllegalArgumentException {

        this.uid = id;
    }
    public void setAssignTime (Long assignTime) throws IllegalArgumentException {
        
        this.assignTime = assignTime;
    }
    public void setModifiedTime (Long modifiedTime) throws IllegalArgumentException {

        this.modifiedTime = modifiedTime;
    }
    public String getUserName() {
        return this.userName;
    }
    public int getUidAssignment() {
        return this.uid;
    }
    public Long getAssignTime() {
        return this.assignTime;
    }
    public Long getModifiedTime() {
        return this.modifiedTime;
    }

}
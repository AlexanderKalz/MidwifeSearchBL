package de.drkalz.midwifesearchbl;

/**
 * Created by Alex on 27.02.16.
 */
public class User {

    private String objectId;
    private String eMail;
    private boolean isMidwife;
    private UserProperties Address;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public boolean isMidwife() {
        return isMidwife;
    }

    public void setMidwife(boolean midwife) {
        isMidwife = midwife;
    }

    public UserProperties getAddress() {
        return Address;
    }

    public void setAddress(UserProperties address) {
        Address = address;
    }
}

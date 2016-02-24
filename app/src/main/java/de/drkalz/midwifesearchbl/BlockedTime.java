package de.drkalz.midwifesearchbl;

import java.util.Date;

/**
 * Created by Alex on 24.02.16.
 */
public class BlockedTime {
    private Date startOfBlock;
    private Date endOfBlock;
    private String midwifeID;
    private String objectId;

    public Date getStartOfBlock() {
        return startOfBlock;
    }

    public void setStartOfBlock(Date startOfBlock) {
        this.startOfBlock = startOfBlock;
    }

    public Date getEndOfBlock() {
        return endOfBlock;
    }

    public void setEndOfBlock(Date endOfBlock) {
        this.endOfBlock = endOfBlock;
    }

    public String getMidwifeID() {
        return midwifeID;
    }

    public void setMidwifeID(String midwifeID) {
        this.midwifeID = midwifeID;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}

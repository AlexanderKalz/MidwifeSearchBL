package de.drkalz.midwifesearchbl.dataObjects;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;

public class Request {
    private String objectId;
    private java.util.Date dateOfBirth;
    private String ownerId;
    private String midwifeID;
    private java.util.Date created;
    private java.util.Date updated;

    public static Request findById(String id) {
        return Backendless.Data.of(Request.class).findById(id);
    }

    public static Future<Request> findByIdAsync(String id) {
        if (Backendless.isAndroid()) {
            throw new UnsupportedOperationException("Using this method is restricted in Android");
        } else {
            Future<Request> future = new Future<>();
            Backendless.Data.of(Request.class).findById(id, future);

            return future;
        }
    }

    public static void findByIdAsync(String id, AsyncCallback<Request> callback) {
        Backendless.Data.of(Request.class).findById(id, callback);
    }

    public static Request findFirst() {
        return Backendless.Data.of(Request.class).findFirst();
    }

    public static Future<Request> findFirstAsync() {
        if (Backendless.isAndroid()) {
            throw new UnsupportedOperationException("Using this method is restricted in Android");
        } else {
            Future<Request> future = new Future<>();
            Backendless.Data.of(Request.class).findFirst(future);

            return future;
        }
    }

    public static void findFirstAsync(AsyncCallback<Request> callback) {
        Backendless.Data.of(Request.class).findFirst(callback);
    }

    public static Request findLast() {
        return Backendless.Data.of(Request.class).findLast();
    }

    public static Future<Request> findLastAsync() {
        if (Backendless.isAndroid()) {
            throw new UnsupportedOperationException("Using this method is restricted in Android");
        } else {
            Future<Request> future = new Future<>();
            Backendless.Data.of(Request.class).findLast(future);

            return future;
        }
    }

    public static void findLastAsync(AsyncCallback<Request> callback) {
        Backendless.Data.of(Request.class).findLast(callback);
    }

    public static BackendlessCollection<Request> find(BackendlessDataQuery query) {
        return Backendless.Data.of(Request.class).find(query);
    }

    public static Future<BackendlessCollection<Request>> findAsync(BackendlessDataQuery query) {
        if (Backendless.isAndroid()) {
            throw new UnsupportedOperationException("Using this method is restricted in Android");
        } else {
            Future<BackendlessCollection<Request>> future = new Future<>();
            Backendless.Data.of(Request.class).find(query, future);

            return future;
        }
    }

    public static void findAsync(BackendlessDataQuery query, AsyncCallback<BackendlessCollection<Request>> callback) {
        Backendless.Data.of(Request.class).find(query, callback);
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public java.util.Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(java.util.Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getMidwifeID() {
        return midwifeID;
    }

    public void setMidwifeID(String midwifeID) {
        this.midwifeID = midwifeID;
    }

    public java.util.Date getCreated() {
        return created;
    }

    public java.util.Date getUpdated() {
        return updated;
    }

    public Request save() {
        return Backendless.Data.of(Request.class).save(this);
    }

    public Future<Request> saveAsync() {
        if (Backendless.isAndroid()) {
            throw new UnsupportedOperationException("Using this method is restricted in Android");
        } else {
            Future<Request> future = new Future<>();
            Backendless.Data.of(Request.class).save(this, future);

            return future;
        }
    }

    public void saveAsync(AsyncCallback<Request> callback) {
        Backendless.Data.of(Request.class).save(this, callback);
    }

    public Long remove() {
        return Backendless.Data.of(Request.class).remove(this);
    }

    public Future<Long> removeAsync() {
        if (Backendless.isAndroid()) {
            throw new UnsupportedOperationException("Using this method is restricted in Android");
        } else {
            Future<Long> future = new Future<>();
            Backendless.Data.of(Request.class).remove(this, future);

            return future;
        }
    }

    public void removeAsync(AsyncCallback<Long> callback) {
        Backendless.Data.of(Request.class).remove(this, callback);
    }
}
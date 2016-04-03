package de.drkalz.midwifesearchbl.dataObjects;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

public class ServiceArea
{
  private java.util.Date created;
  private String ownerId;
  private java.util.Date updated;
  private String objectId;
  private Double radius;
  private GeoPoint servicePoint;
  private String midwifeID;

  public static ServiceArea findById(String id)
  {
    return Backendless.Data.of( ServiceArea.class ).findById( id );
  }

  public static Future<ServiceArea> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<ServiceArea> future = new Future<>();
      Backendless.Data.of( ServiceArea.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<ServiceArea> callback )
  {
    Backendless.Data.of( ServiceArea.class ).findById( id, callback );
  }

  public static ServiceArea findFirst()
  {
    return Backendless.Data.of( ServiceArea.class ).findFirst();
  }

  public static Future<ServiceArea> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<ServiceArea> future = new Future<>();
      Backendless.Data.of( ServiceArea.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<ServiceArea> callback )
  {
    Backendless.Data.of( ServiceArea.class ).findFirst( callback );
  }

  public static ServiceArea findLast()
  {
    return Backendless.Data.of( ServiceArea.class ).findLast();
  }

  public static Future<ServiceArea> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<ServiceArea> future = new Future<>();
      Backendless.Data.of( ServiceArea.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<ServiceArea> callback )
  {
    Backendless.Data.of( ServiceArea.class ).findLast( callback );
  }

  public static BackendlessCollection<ServiceArea> find(BackendlessDataQuery query )
  {
    return Backendless.Data.of( ServiceArea.class ).find( query );
  }

  public static Future<BackendlessCollection<ServiceArea>> findAsync(BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<ServiceArea>> future = new Future<>();
      Backendless.Data.of( ServiceArea.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync(BackendlessDataQuery query, AsyncCallback<BackendlessCollection<ServiceArea>> callback )
  {
    Backendless.Data.of( ServiceArea.class ).find( query, callback );
  }

  public String getMidwifeID() {
    return midwifeID;
  }

  public void setMidwifeID(String midwifeID) {
    this.midwifeID = midwifeID;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public void setObjectId(String objectId) {
    this.objectId = objectId;
  }

  public Double getRadius()
  {
    return radius;
  }

  public void setRadius( Double radius )
  {
    this.radius = radius;
  }

  public GeoPoint getServicePoint()
  {
    return servicePoint;
  }

  public void setServicePoint( GeoPoint servicePoint )
  {
    this.servicePoint = servicePoint;
  }

  public ServiceArea save()
  {
    return Backendless.Data.of( ServiceArea.class ).save( this );
  }

  public Future<ServiceArea> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<ServiceArea> future = new Future<>();
      Backendless.Data.of( ServiceArea.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<ServiceArea> callback )
  {
    Backendless.Data.of( ServiceArea.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( ServiceArea.class ).remove( this );
  }

  public Future<Long> removeAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Long> future = new Future<>();
      Backendless.Data.of( ServiceArea.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( ServiceArea.class ).remove( this, callback );
  }
}
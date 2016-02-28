package de.drkalz.midwifesearchbl.DataObjects;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;

public class MidwifeList
{
  private java.util.Date created;
  private java.util.Date updated;
  private String objectId;
  private String ownerId;
  private BlockedTime hasBlockedTimes;
  private ServicePortfolio hasService;
  private java.util.List<ServiceArea> areaOfService;

  public java.util.Date getCreated()
  {
    return created;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public BlockedTime getHasBlockedTimes()
  {
    return hasBlockedTimes;
  }

  public void setHasBlockedTimes( BlockedTime hasBlockedTimes )
  {
    this.hasBlockedTimes = hasBlockedTimes;
  }

  public ServicePortfolio getHasService()
  {
    return hasService;
  }

  public void setHasService( ServicePortfolio hasService )
  {
    this.hasService = hasService;
  }

  public java.util.List<ServiceArea> getAreaOfService()
  {
    return areaOfService;
  }

  public void setAreaOfService( java.util.List<ServiceArea> areaOfService )
  {
    this.areaOfService = areaOfService;
  }

                                                    
  public MidwifeList save()
  {
    return Backendless.Data.of( MidwifeList.class ).save( this );
  }

  public Future<MidwifeList> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<MidwifeList> future = new Future<MidwifeList>();
      Backendless.Data.of( MidwifeList.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<MidwifeList> callback )
  {
    Backendless.Data.of( MidwifeList.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( MidwifeList.class ).remove( this );
  }

  public Future<Long> removeAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Long> future = new Future<Long>();
      Backendless.Data.of( MidwifeList.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( MidwifeList.class ).remove( this, callback );
  }

  public static MidwifeList findById( String id )
  {
    return Backendless.Data.of( MidwifeList.class ).findById( id );
  }

  public static Future<MidwifeList> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<MidwifeList> future = new Future<MidwifeList>();
      Backendless.Data.of( MidwifeList.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<MidwifeList> callback )
  {
    Backendless.Data.of( MidwifeList.class ).findById( id, callback );
  }

  public static MidwifeList findFirst()
  {
    return Backendless.Data.of( MidwifeList.class ).findFirst();
  }

  public static Future<MidwifeList> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<MidwifeList> future = new Future<MidwifeList>();
      Backendless.Data.of( MidwifeList.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<MidwifeList> callback )
  {
    Backendless.Data.of( MidwifeList.class ).findFirst( callback );
  }

  public static MidwifeList findLast()
  {
    return Backendless.Data.of( MidwifeList.class ).findLast();
  }

  public static Future<MidwifeList> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<MidwifeList> future = new Future<MidwifeList>();
      Backendless.Data.of( MidwifeList.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<MidwifeList> callback )
  {
    Backendless.Data.of( MidwifeList.class ).findLast( callback );
  }

  public static BackendlessCollection<MidwifeList> find(BackendlessDataQuery query )
  {
    return Backendless.Data.of( MidwifeList.class ).find( query );
  }

  public static Future<BackendlessCollection<MidwifeList>> findAsync(BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<MidwifeList>> future = new Future<BackendlessCollection<MidwifeList>>();
      Backendless.Data.of( MidwifeList.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync(BackendlessDataQuery query, AsyncCallback<BackendlessCollection<MidwifeList>> callback )
  {
    Backendless.Data.of( MidwifeList.class ).find( query, callback );
  }
}
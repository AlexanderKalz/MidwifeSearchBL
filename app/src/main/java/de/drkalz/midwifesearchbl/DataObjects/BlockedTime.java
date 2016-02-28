package de.drkalz.midwifesearchbl.DataObjects;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;

public class BlockedTime
{
  private java.util.Date updated;
  private String objectId;
  private java.util.Date created;
  private java.util.Date endOfBlock;
  private String ownerId;
  private java.util.Date startOfBlock;
  public java.util.Date getUpdated()
  {
    return updated;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public java.util.Date getEndOfBlock()
  {
    return endOfBlock;
  }

  public void setEndOfBlock( java.util.Date endOfBlock )
  {
    this.endOfBlock = endOfBlock;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public java.util.Date getStartOfBlock()
  {
    return startOfBlock;
  }

  public void setStartOfBlock( java.util.Date startOfBlock )
  {
    this.startOfBlock = startOfBlock;
  }

                                                    
  public BlockedTime save()
  {
    return Backendless.Data.of( BlockedTime.class ).save( this );
  }

  public Future<BlockedTime> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BlockedTime> future = new Future<BlockedTime>();
      Backendless.Data.of( BlockedTime.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<BlockedTime> callback )
  {
    Backendless.Data.of( BlockedTime.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( BlockedTime.class ).remove( this );
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
      Backendless.Data.of( BlockedTime.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( BlockedTime.class ).remove( this, callback );
  }

  public static BlockedTime findById( String id )
  {
    return Backendless.Data.of( BlockedTime.class ).findById( id );
  }

  public static Future<BlockedTime> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BlockedTime> future = new Future<BlockedTime>();
      Backendless.Data.of( BlockedTime.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<BlockedTime> callback )
  {
    Backendless.Data.of( BlockedTime.class ).findById( id, callback );
  }

  public static BlockedTime findFirst()
  {
    return Backendless.Data.of( BlockedTime.class ).findFirst();
  }

  public static Future<BlockedTime> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BlockedTime> future = new Future<BlockedTime>();
      Backendless.Data.of( BlockedTime.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<BlockedTime> callback )
  {
    Backendless.Data.of( BlockedTime.class ).findFirst( callback );
  }

  public static BlockedTime findLast()
  {
    return Backendless.Data.of( BlockedTime.class ).findLast();
  }

  public static Future<BlockedTime> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BlockedTime> future = new Future<BlockedTime>();
      Backendless.Data.of( BlockedTime.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<BlockedTime> callback )
  {
    Backendless.Data.of( BlockedTime.class ).findLast( callback );
  }

  public static BackendlessCollection<BlockedTime> find(BackendlessDataQuery query )
  {
    return Backendless.Data.of( BlockedTime.class ).find( query );
  }

  public static Future<BackendlessCollection<BlockedTime>> findAsync(BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<BlockedTime>> future = new Future<BackendlessCollection<BlockedTime>>();
      Backendless.Data.of( BlockedTime.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync(BackendlessDataQuery query, AsyncCallback<BackendlessCollection<BlockedTime>> callback )
  {
    Backendless.Data.of( BlockedTime.class ).find( query, callback );
  }
}
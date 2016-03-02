package de.drkalz.midwifesearchbl.dataObjects;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;

public class UserAddress
{
  private String ownerId;
  private String telefon;
  private Integer zip;
  private String firstname;
  private String mobil;
  private String city;
  private java.util.Date created;
  private String country;
  private String objectId;
  private String lastname;
  private String street;
  private java.util.Date updated;
  private String homepage;

  public static UserAddress findById( String id )
  {
    return Backendless.Data.of( UserAddress.class ).findById( id );
  }

  public static Future<UserAddress> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<UserAddress> future = new Future<UserAddress>();
      Backendless.Data.of( UserAddress.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<UserAddress> callback )
  {
    Backendless.Data.of( UserAddress.class ).findById( id, callback );
  }

  public static UserAddress findFirst()
  {
    return Backendless.Data.of( UserAddress.class ).findFirst();
  }

  public static Future<UserAddress> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<UserAddress> future = new Future<UserAddress>();
      Backendless.Data.of( UserAddress.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<UserAddress> callback )
  {
    Backendless.Data.of( UserAddress.class ).findFirst( callback );
  }

  public static UserAddress findLast()
  {
    return Backendless.Data.of( UserAddress.class ).findLast();
  }

  public static Future<UserAddress> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<UserAddress> future = new Future<UserAddress>();
      Backendless.Data.of( UserAddress.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<UserAddress> callback )
  {
    Backendless.Data.of( UserAddress.class ).findLast( callback );
  }

  public static BackendlessCollection<UserAddress> find(BackendlessDataQuery query )
  {
    return Backendless.Data.of( UserAddress.class ).find( query );
  }

  public static Future<BackendlessCollection<UserAddress>> findAsync(BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<UserAddress>> future = new Future<BackendlessCollection<UserAddress>>();
      Backendless.Data.of( UserAddress.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync(BackendlessDataQuery query, AsyncCallback<BackendlessCollection<UserAddress>> callback )
  {
    Backendless.Data.of( UserAddress.class ).find( query, callback );
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public String getTelefon()
  {
    return telefon;
  }

  public void setTelefon( String telefon )
  {
    this.telefon = telefon;
  }

  public Integer getZip()
  {
    return zip;
  }

  public void setZip( Integer zip )
  {
    this.zip = zip;
  }

  public String getFirstname()
  {
    return firstname;
  }

  public void setFirstname( String firstname )
  {
    this.firstname = firstname;
  }

  public String getMobil()
  {
    return mobil;
  }

  public void setMobil( String mobil )
  {
    this.mobil = mobil;
  }

  public String getCity()
  {
    return city;
  }

  public void setCity( String city )
  {
    this.city = city;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public String getCountry()
  {
    return country;
  }

  public void setCountry( String country )
  {
    this.country = country;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public String getLastname()
  {
    return lastname;
  }

  public void setLastname( String lastname )
  {
    this.lastname = lastname;
  }

  public String getStreet()
  {
    return street;
  }

  public void setStreet( String street )
  {
    this.street = street;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public String getHomepage()
  {
    return homepage;
  }

  public void setHomepage( String homepage )
  {
    this.homepage = homepage;
  }

  public UserAddress save()
  {
    return Backendless.Data.of( UserAddress.class ).save( this );
  }

  public Future<UserAddress> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<UserAddress> future = new Future<UserAddress>();
      Backendless.Data.of( UserAddress.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<UserAddress> callback )
  {
    Backendless.Data.of( UserAddress.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( UserAddress.class ).remove( this );
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
      Backendless.Data.of( UserAddress.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( UserAddress.class ).remove( this, callback );
  }
}
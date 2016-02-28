package de.drkalz.midwifesearchbl.DataObjects;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;

public class ServicePortfolio
{
  private Boolean geburtsVorberetiung;
  private Boolean wochenBetreuung;
  private Boolean schwangerenVorsorge;
  private Boolean german;
  private String objectId;
  private Boolean english;
  private Boolean hausGeburt;
  private Boolean geburtHge;
  private java.util.Date updated;
  private Boolean french;
  private java.util.Date created;
  private Boolean belegGeburt;
  private Boolean rueckbildungsKurs;
  private String ownerId;
  private Boolean spanish;
  public Boolean getGeburtsVorberetiung()
  {
    return geburtsVorberetiung;
  }

  public void setGeburtsVorberetiung( Boolean geburtsVorberetiung )
  {
    this.geburtsVorberetiung = geburtsVorberetiung;
  }

  public Boolean getWochenBetreuung()
  {
    return wochenBetreuung;
  }

  public void setWochenBetreuung( Boolean wochenBetreuung )
  {
    this.wochenBetreuung = wochenBetreuung;
  }

  public Boolean getSchwangerenVorsorge()
  {
    return schwangerenVorsorge;
  }

  public void setSchwangerenVorsorge( Boolean schwangerenVorsorge )
  {
    this.schwangerenVorsorge = schwangerenVorsorge;
  }

  public Boolean getGerman()
  {
    return german;
  }

  public void setGerman( Boolean german )
  {
    this.german = german;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public Boolean getEnglish()
  {
    return english;
  }

  public void setEnglish( Boolean english )
  {
    this.english = english;
  }

  public Boolean getHausGeburt()
  {
    return hausGeburt;
  }

  public void setHausGeburt( Boolean hausGeburt )
  {
    this.hausGeburt = hausGeburt;
  }

  public Boolean getGeburtHge()
  {
    return geburtHge;
  }

  public void setGeburtHge( Boolean geburtHge )
  {
    this.geburtHge = geburtHge;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public Boolean getFrench()
  {
    return french;
  }

  public void setFrench( Boolean french )
  {
    this.french = french;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public Boolean getBelegGeburt()
  {
    return belegGeburt;
  }

  public void setBelegGeburt( Boolean belegGeburt )
  {
    this.belegGeburt = belegGeburt;
  }

  public Boolean getRueckbildungsKurs()
  {
    return rueckbildungsKurs;
  }

  public void setRueckbildungsKurs( Boolean rueckbildungsKurs )
  {
    this.rueckbildungsKurs = rueckbildungsKurs;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public Boolean getSpanish()
  {
    return spanish;
  }

  public void setSpanish( Boolean spanish )
  {
    this.spanish = spanish;
  }

                                                    
  public ServicePortfolio save()
  {
    return Backendless.Data.of( ServicePortfolio.class ).save( this );
  }

  public Future<ServicePortfolio> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<ServicePortfolio> future = new Future<ServicePortfolio>();
      Backendless.Data.of( ServicePortfolio.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<ServicePortfolio> callback )
  {
    Backendless.Data.of( ServicePortfolio.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( ServicePortfolio.class ).remove( this );
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
      Backendless.Data.of( ServicePortfolio.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( ServicePortfolio.class ).remove( this, callback );
  }

  public static ServicePortfolio findById( String id )
  {
    return Backendless.Data.of( ServicePortfolio.class ).findById( id );
  }

  public static Future<ServicePortfolio> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<ServicePortfolio> future = new Future<ServicePortfolio>();
      Backendless.Data.of( ServicePortfolio.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<ServicePortfolio> callback )
  {
    Backendless.Data.of( ServicePortfolio.class ).findById( id, callback );
  }

  public static ServicePortfolio findFirst()
  {
    return Backendless.Data.of( ServicePortfolio.class ).findFirst();
  }

  public static Future<ServicePortfolio> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<ServicePortfolio> future = new Future<ServicePortfolio>();
      Backendless.Data.of( ServicePortfolio.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<ServicePortfolio> callback )
  {
    Backendless.Data.of( ServicePortfolio.class ).findFirst( callback );
  }

  public static ServicePortfolio findLast()
  {
    return Backendless.Data.of( ServicePortfolio.class ).findLast();
  }

  public static Future<ServicePortfolio> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<ServicePortfolio> future = new Future<ServicePortfolio>();
      Backendless.Data.of( ServicePortfolio.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<ServicePortfolio> callback )
  {
    Backendless.Data.of( ServicePortfolio.class ).findLast( callback );
  }

  public static BackendlessCollection<ServicePortfolio> find(BackendlessDataQuery query )
  {
    return Backendless.Data.of( ServicePortfolio.class ).find( query );
  }

  public static Future<BackendlessCollection<ServicePortfolio>> findAsync(BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<ServicePortfolio>> future = new Future<BackendlessCollection<ServicePortfolio>>();
      Backendless.Data.of( ServicePortfolio.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync(BackendlessDataQuery query, AsyncCallback<BackendlessCollection<ServicePortfolio>> callback )
  {
    Backendless.Data.of( ServicePortfolio.class ).find( query, callback );
  }
}
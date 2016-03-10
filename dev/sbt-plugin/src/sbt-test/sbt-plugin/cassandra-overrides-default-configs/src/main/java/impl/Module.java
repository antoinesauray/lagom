package impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import api.FooService;
import play.*;
import javax.inject.Inject;
import java.util.Date;
import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;

public class Module extends AbstractModule implements ServiceGuiceSupport {
	@Override
	protected void configure() {
		bindServices(serviceBinding(FooService.class, FooServiceImpl.class));
		bind(OnStart.class).asEagerSingleton();
	}
}

class OnStart {

  public static String CASSANDRA_JOURNAL_KEYSPACE        = "cassandra-journal.keyspace";
  public static String CASSANDRA_JOURNAL_PORT            = "cassandra-journal.port";
  public static String CASSANDRA_SNAPSHOT_STORE_KEYSPACE = "cassandra-snapshot-store.keyspace";
  public static String CASSANDRA_SNAPSHOT_STORE_PORT     = "cassandra-snapshot-store.port";
  public static String LAGOM_CASSANDRA_READ_KEYSPACE     = "lagom.persistence.read-side.cassandra.keyspace";
  public static String LAGOM_CASSANDRA_READ_PORT         = "lagom.persistence.read-side.cassandra.port";
  
  @Inject
  public OnStart(Application app) {
  	dumpInjectedCassandraConfig(app);
  }

  private void dumpInjectedCassandraConfig(Application app) {
    Configuration config = app.configuration();
    ArrayList<String> keys = new ArrayList<>(Arrays.asList(CASSANDRA_JOURNAL_KEYSPACE, CASSANDRA_JOURNAL_PORT, 
      CASSANDRA_SNAPSHOT_STORE_KEYSPACE, CASSANDRA_SNAPSHOT_STORE_PORT,
      LAGOM_CASSANDRA_READ_KEYSPACE, LAGOM_CASSANDRA_READ_PORT));

    try(FileWriter writer = new FileWriter(app.getFile("target/injected-cassandra.conf"), true)) {
      for(String key: keys) {
        String value = config.getString(key);
        writer.write(key + "="+value+"\n");
      }
    }
    catch(IOException e) {
      throw new RuntimeException(e);
    }
  }
}

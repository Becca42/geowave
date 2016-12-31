package mil.nga.giat.geowave.test;

import org.apache.log4j.Logger;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import mil.nga.giat.geowave.core.store.DataStore;
import mil.nga.giat.geowave.core.store.GenericStoreFactory;
import mil.nga.giat.geowave.core.store.StoreFactoryOptions;
import mil.nga.giat.geowave.datastore.cassandra.CassandraDataStoreFactory;
import mil.nga.giat.geowave.datastore.cassandra.operations.config.CassandraRequiredOptions;
import mil.nga.giat.geowave.datastore.hbase.operations.config.HBaseRequiredOptions;
import mil.nga.giat.geowave.test.annotation.GeoWaveTestStore.GeoWaveStoreType;

public class CassandraStoreTestEnvironment extends
		StoreTestEnvironment
{
	private static GenericStoreFactory<DataStore> STORE_FACTORY = new CassandraDataStoreFactory();
	private static CassandraStoreTestEnvironment singletonInstance = null;

	private Cluster cluster;
	private Session session;

	public static synchronized CassandraStoreTestEnvironment getInstance() {
		if (singletonInstance == null) {
			singletonInstance = new CassandraStoreTestEnvironment();
		}
		return singletonInstance;
	}

	private final static Logger LOGGER = Logger.getLogger(CassandraStoreTestEnvironment.class);

	private CassandraStoreTestEnvironment() {}

	@Override
	protected void initOptions(
			final StoreFactoryOptions options ) {
		((CassandraRequiredOptions) options).setContactPoint("127.0.0.1");
	}

	@Override
	protected GenericStoreFactory<DataStore> getDataStoreFactory() {
		return STORE_FACTORY;
	}

	@Override
	public void setup() {
//		try {
//			if (cluster == null) {
//				cluster = Cluster.builder().addContactPoint(
//						"127.0.0.1").build();
//			}
//
//			session = cluster.connect();
//
//			LOGGER.info("Opened connection to cassandra cluster!");
//		}
//		catch (Exception e) {
//			LOGGER.error(
//					"Failed to connect to Cassandra test cluster",
//					e);
//		}
	}

	@Override
	public void tearDown() {
		try {
			session.close();
			cluster.close();
		}
		catch (Exception e) {
			LOGGER.error(
					"Error shutting down Cassandra test cluster",
					e);
		}
	}

	@Override
	protected GeoWaveStoreType getStoreType() {
		return GeoWaveStoreType.CASSANDRA;
	}

	@Override
	public TestEnvironment[] getDependentEnvironments() {
		return new TestEnvironment[] {};
	}
}

package mil.nga.giat.geowave.adapter.vector.query;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import mil.nga.giat.geowave.adapter.vector.plugin.GeoWaveGTDataStoreFactory;
import mil.nga.giat.geowave.adapter.vector.stats.FeatureTimeRangeStatistics;
import mil.nga.giat.geowave.adapter.vector.utils.DateUtilities;
import mil.nga.giat.geowave.core.geotime.store.query.TemporalRange;
import mil.nga.giat.geowave.core.index.ByteArrayId;
import mil.nga.giat.geowave.core.store.memory.MemoryStoreFactoryFamily;

import org.geotools.data.DataStore;
import org.geotools.data.DataUtilities;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.FeatureWriter;
import org.geotools.data.Transaction;
import org.geotools.feature.SchemaException;
import org.geotools.filter.text.cql2.CQLException;
import org.junit.Before;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;

public class TemporalRangeTest
{
	DataStore dataStore;
	SimpleFeatureType type;
	GeometryFactory factory = new GeometryFactory(
			new PrecisionModel(
					PrecisionModel.FIXED));

	private DataStore createDataStore()
			throws IOException {
		final Map<String, Serializable> params = new HashMap<String, Serializable>();
		params.put(
				"gwNamespace",
				"test");
		return new GeoWaveGTDataStoreFactory(
				new MemoryStoreFactoryFamily()).createNewDataStore(params);
	}

	@Before
	public void setup()
			throws SchemaException,
			CQLException,
			IOException {
		dataStore = createDataStore();
		type = DataUtilities.createType(
				"geostuff",
				"geometry:Geometry:srid=4326,pop:java.lang.Long,pid:String,when:Date");

		dataStore.createSchema(type);
	}

	@Test
	public void test()
			throws ParseException,
			IOException {
		final Calendar gmt = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		final Calendar local = Calendar.getInstance(TimeZone.getTimeZone("EDT"));
		local.setTimeInMillis(gmt.getTimeInMillis());
		final TemporalRange rGmt = new TemporalRange(
				gmt.getTime(),
				gmt.getTime());
		final TemporalRange rLocal = new TemporalRange(
				local.getTime(),
				local.getTime());
		rGmt.fromBinary(rGmt.toBinary());
		assertEquals(
				gmt.getTime(),
				rGmt.getEndTime());
		assertEquals(
				rLocal.getEndTime(),
				rGmt.getEndTime());
		assertEquals(
				rLocal.getEndTime().getTime(),
				rGmt.getEndTime().getTime());

		final Transaction transaction1 = new DefaultTransaction();

		final FeatureWriter<SimpleFeatureType, SimpleFeature> writer = dataStore.getFeatureWriter(
				type.getTypeName(),
				transaction1);
		final SimpleFeature newFeature = writer.next();
		newFeature.setAttribute(
				"pop",
				Long.valueOf(77));
		newFeature.setAttribute(
				"pid",
				UUID.randomUUID().toString());
		newFeature.setAttribute(
				"when",
				DateUtilities.parseISO("2005-05-19T19:32:56-04:00"));
		newFeature.setAttribute(
				"geometry",
				factory.createPoint(new Coordinate(
						43.454,
						28.232)));

		final FeatureTimeRangeStatistics stats = new FeatureTimeRangeStatistics(
				new ByteArrayId(
						"a"),
				"when");
		stats.entryIngested(
				null,
				newFeature);

		assertEquals(
				DateUtilities.parseISO("2005-05-19T23:32:56Z"),
				stats.asTemporalRange().getStartTime());
	}

}

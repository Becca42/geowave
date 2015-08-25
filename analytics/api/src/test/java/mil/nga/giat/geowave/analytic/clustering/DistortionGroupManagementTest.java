package mil.nga.giat.geowave.analytic.clustering;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import mil.nga.giat.geowave.adapter.vector.FeatureDataAdapter;
import mil.nga.giat.geowave.analytic.AnalyticFeature;
import mil.nga.giat.geowave.analytic.AnalyticItemWrapper;
import mil.nga.giat.geowave.analytic.SimpleFeatureItemWrapperFactory;
import mil.nga.giat.geowave.analytic.clustering.DistortionGroupManagement.DistortionDataAdapter;
import mil.nga.giat.geowave.analytic.clustering.DistortionGroupManagement.DistortionEntry;
import mil.nga.giat.geowave.core.geotime.IndexType;
import mil.nga.giat.geowave.core.index.StringUtils;
import mil.nga.giat.geowave.core.store.DataStore;
import mil.nga.giat.geowave.core.store.adapter.AdapterStore;
import mil.nga.giat.geowave.core.store.index.Index;
import mil.nga.giat.geowave.core.store.index.IndexStore;
import mil.nga.giat.geowave.core.store.memory.MemoryAdapterStore;
import mil.nga.giat.geowave.core.store.memory.MemoryDataStore;
import mil.nga.giat.geowave.core.store.memory.MemoryIndexStore;

import org.geotools.feature.type.BasicFeatureTypes;
import org.junit.Before;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;

public class DistortionGroupManagementTest
{

	final GeometryFactory factory = new GeometryFactory();
	final SimpleFeatureType ftype;
	final Index index = IndexType.SPATIAL_VECTOR.createDefaultIndex();

	final FeatureDataAdapter adapter;
	final DataStore dataStore;
	final AdapterStore adapterStore;
	final IndexStore indexStore;

	public DistortionGroupManagementTest() {
		ftype = AnalyticFeature.createGeometryFeatureAdapter(
				"centroid",
				new String[] {
					"extra1"
				},
				BasicFeatureTypes.DEFAULT_NAMESPACE,
				ClusteringUtils.CLUSTERING_CRS).getType();
		adapter = new FeatureDataAdapter(
				ftype);

		dataStore = new MemoryDataStore();

		adapterStore = new MemoryAdapterStore();
		adapterStore.addAdapter(adapter);
		indexStore = new MemoryIndexStore();
		indexStore.addIndex(index);
	}

	private void addDistortion(
			final String grp,
			final int count,
			final Double distortion ) {
		dataStore.ingest(
				new DistortionDataAdapter(),
				DistortionGroupManagement.DISTORTIONS_INDEX,
				new DistortionEntry(
						grp,
						count,
						distortion));

	}

	@Before
	public void setup() {

		final DataStore dataStore = new MemoryDataStore();
		// big jump for grp1 between batch 2 and 3
		// big jump for grp2 between batch 1 and 2
		// thus, the jump occurs for different groups between different batches!

		// b1
		addDistortion(
				"grp1",
				1,
				0.1);
		addDistortion(
				"grp2",
				1,
				0.1);
		// b2
		addDistortion(
				"grp1",
				2,
				0.2);
		addDistortion(
				"grp2",
				2,
				0.3);
		// b3
		addDistortion(
				"grp1",
				3,
				0.4);
		addDistortion(
				"grp2",
				3,
				0.4);

		dataStore.ingest(
				adapter,
				index,
				AnalyticFeature.createGeometryFeature(
						ftype,
						"b1_1",
						"123",
						"fred",
						"grp1",
						20.30203,
						factory.createPoint(new Coordinate(
								02.33,
								0.23)),
						new String[] {
							"extra1"
						},
						new double[] {
							0.022
						},
						1,
						1,
						0));

		dataStore.ingest(
				adapter,
				index,
				AnalyticFeature.createGeometryFeature(
						ftype,
						"b1_1",
						"124",
						"barney",
						"grp1",
						20.30203,
						factory.createPoint(new Coordinate(
								02.33,
								0.23)),
						new String[] {
							"extra1"
						},
						new double[] {
							0.022
						},
						1,
						1,
						0));

		dataStore.ingest(
				adapter,
				index,
				AnalyticFeature.createGeometryFeature(
						ftype,
						"b1_1",
						"125",
						"wilma",
						"grp2",
						20.30203,
						factory.createPoint(new Coordinate(
								02.33,
								0.23)),
						new String[] {
							"extra1"
						},
						new double[] {
							0.022
						},
						1,
						1,
						0));

		dataStore.ingest(
				adapter,
				index,
				AnalyticFeature.createGeometryFeature(
						ftype,
						"b1_1",
						"126",
						"betty",
						"grp2",
						20.30203,
						factory.createPoint(new Coordinate(
								02.33,
								0.23)),
						new String[] {
							"extra1"
						},
						new double[] {
							0.022
						},
						1,
						1,
						0));

		dataStore.ingest(
				adapter,
				index,
				AnalyticFeature.createGeometryFeature(
						ftype,
						"b1_2",
						"130",
						"dusty",
						"grp1",
						20.30203,
						factory.createPoint(new Coordinate(
								02.33,
								0.23)),
						new String[] {
							"extra1"
						},
						new double[] {
							0.022
						},
						1,
						1,
						0));

		dataStore.ingest(
				adapter,
				index,
				AnalyticFeature.createGeometryFeature(
						ftype,
						"b1_2",
						"131",
						"dino",
						"grp1",
						20.30203,
						factory.createPoint(new Coordinate(
								02.33,
								0.23)),
						new String[] {
							"extra1"
						},
						new double[] {
							0.022
						},
						1,
						1,
						0));

		dataStore.ingest(
				adapter,
				index,
				AnalyticFeature.createGeometryFeature(
						ftype,
						"b1_2",
						"127",
						"bamm-bamm",
						"grp2",
						20.30203,
						factory.createPoint(new Coordinate(
								02.33,
								0.23)),
						new String[] {
							"extra1"
						},
						new double[] {
							0.022
						},
						1,
						1,
						0));

		dataStore.ingest(
				adapter,
				index,
				AnalyticFeature.createGeometryFeature(
						ftype,
						"b1_2",
						"128",
						"chip",
						"grp2",
						20.30203,
						factory.createPoint(new Coordinate(
								02.33,
								0.23)),
						new String[] {
							"extra1"
						},
						new double[] {
							0.022
						},
						1,
						1,
						0));

		dataStore.ingest(
				adapter,
				index,
				AnalyticFeature.createGeometryFeature(
						ftype,
						"b1_3",
						"140",
						"pearl",
						"grp1",
						20.30203,
						factory.createPoint(new Coordinate(
								02.33,
								0.23)),
						new String[] {
							"extra1"
						},
						new double[] {
							0.022
						},
						1,
						1,
						0));

		dataStore.ingest(
				adapter,
				index,
				AnalyticFeature.createGeometryFeature(
						ftype,
						"b1_3",
						"141",
						"roxy",
						"grp1",
						20.30203,
						factory.createPoint(new Coordinate(
								02.33,
								0.23)),
						new String[] {
							"extra1"
						},
						new double[] {
							0.022
						},
						1,
						1,
						0));

		dataStore.ingest(
				adapter,
				index,
				AnalyticFeature.createGeometryFeature(
						ftype,
						"b1_3",
						"142",
						"giggles",
						"grp2",
						20.30203,
						factory.createPoint(new Coordinate(
								02.33,
								0.23)),
						new String[] {
							"extra1"
						},
						new double[] {
							0.022
						},
						1,
						1,
						0));

		dataStore.ingest(
				adapter,
				index,
				AnalyticFeature.createGeometryFeature(
						ftype,
						"b1_3",
						"143",
						"gazoo",
						"grp2",
						20.30203,
						factory.createPoint(new Coordinate(
								02.33,
								0.23)),
						new String[] {
							"extra1"
						},
						new double[] {
							0.022
						},
						1,
						1,
						0));

	}

	@Test
	public void test()
			throws IOException {
		DistortionGroupManagement.retainBestGroups(
				dataStore,
				indexStore,
				adapterStore,
				new SimpleFeatureItemWrapperFactory(),
				StringUtils.stringFromBinary(adapter.getAdapterId().getBytes()),
				StringUtils.stringFromBinary(index.getId().getBytes()),
				"b1",
				1);
		final CentroidManagerGeoWave<SimpleFeature> centroidManager = new CentroidManagerGeoWave<SimpleFeature>(
				dataStore,
				indexStore,
				adapterStore,
				new SimpleFeatureItemWrapperFactory(),
				StringUtils.stringFromBinary(adapter.getAdapterId().getBytes()),
				StringUtils.stringFromBinary(index.getId().getBytes()),
				"b1",
				1);
		final List<String> groups = centroidManager.getAllCentroidGroups();
		assertEquals(
				2,
				groups.size());
		final boolean groupFound[] = new boolean[2];
		for (final String grpId : groups) {
			final List<AnalyticItemWrapper<SimpleFeature>> items = centroidManager.getCentroidsForGroup(grpId);
			assertEquals(
					2,
					items.size());
			if ("grp1".equals(grpId)) {
				groupFound[0] = true;
				assertTrue("pearl".equals(items.get(
						0).getName()) || "roxy".equals(items.get(
						0).getName()));
			}
			else if ("grp2".equals(grpId)) {
				groupFound[1] = true;
				assertTrue("chip".equals(items.get(
						0).getName()) || "bamm-bamm".equals(items.get(
						0).getName()));
			}
		}
		// each unique group is found?
		int c = 0;
		for (final boolean gf : groupFound) {
			c += (gf ? 1 : 0);
		}
		assertEquals(
				2,
				c);
	}
}
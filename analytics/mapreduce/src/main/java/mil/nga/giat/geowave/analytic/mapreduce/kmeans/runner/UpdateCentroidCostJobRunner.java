package mil.nga.giat.geowave.analytic.mapreduce.kmeans.runner;

import mil.nga.giat.geowave.analytic.PropertyManagement;
import mil.nga.giat.geowave.analytic.clustering.CentroidManagerGeoWave;
import mil.nga.giat.geowave.analytic.clustering.NestedGroupCentroidAssignment;
import mil.nga.giat.geowave.analytic.mapreduce.CountofDoubleWritable;
import mil.nga.giat.geowave.analytic.mapreduce.GeoWaveAnalyticJobRunner;
import mil.nga.giat.geowave.analytic.mapreduce.GeoWaveOutputFormatConfiguration;
import mil.nga.giat.geowave.analytic.mapreduce.GroupIDText;
import mil.nga.giat.geowave.analytic.mapreduce.MapReduceJobRunner;
import mil.nga.giat.geowave.analytic.mapreduce.kmeans.UpdateCentroidCostMapReduce;
import mil.nga.giat.geowave.analytic.param.CentroidParameters;
import mil.nga.giat.geowave.analytic.param.ParameterEnum;
import mil.nga.giat.geowave.mapreduce.output.GeoWaveOutputKey;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.opengis.feature.simple.SimpleFeature;

/**
 * Update the centroid with its cost, measured by the average distance of
 * assigned points.
 * 
 * 
 */
public class UpdateCentroidCostJobRunner extends
		GeoWaveAnalyticJobRunner implements
		MapReduceJobRunner
{

	public UpdateCentroidCostJobRunner() {
		super.setOutputFormatConfiguration(new GeoWaveOutputFormatConfiguration());
	}

	@Override
	public Class<?> getScope() {
		return UpdateCentroidCostMapReduce.class;
	}

	@Override
	public int run(
			final Configuration config,
			final PropertyManagement runTimeProperties )
			throws Exception {

		CentroidManagerGeoWave.setParameters(
				config,
				getScope(),
				runTimeProperties);

		NestedGroupCentroidAssignment.setParameters(
				config,
				getScope(),
				runTimeProperties);
		runTimeProperties.setConfig(
				new ParameterEnum[] {
					CentroidParameters.Centroid.WRAPPER_FACTORY_CLASS
				},
				config,
				getScope());

		return super.run(
				config,
				runTimeProperties);
	}

	@Override
	public void configure(
			final Job job )
			throws Exception {

		job.setMapperClass(UpdateCentroidCostMapReduce.UpdateCentroidCostMap.class);
		job.setMapOutputKeyClass(GroupIDText.class);
		job.setMapOutputValueClass(CountofDoubleWritable.class);
		job.setCombinerClass(UpdateCentroidCostMapReduce.UpdateCentroidCostCombiner.class);
		job.setReducerClass(UpdateCentroidCostMapReduce.UpdateCentroidCostReducer.class);
		job.setReduceSpeculativeExecution(false);
		job.setOutputKeyClass(GeoWaveOutputKey.class);
		job.setOutputValueClass(SimpleFeature.class);
	}

	@Override
	protected String getJobName() {
		return "Update Centroid Cost";
	}
}
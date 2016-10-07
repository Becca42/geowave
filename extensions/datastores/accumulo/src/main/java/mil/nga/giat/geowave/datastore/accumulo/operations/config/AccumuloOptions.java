package mil.nga.giat.geowave.datastore.accumulo.operations.config;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParametersDelegate;

import mil.nga.giat.geowave.core.store.DataStoreOptions;
import mil.nga.giat.geowave.core.store.base.BaseDataStoreOptions;

/**
 * This class can be used to modify the behavior of the Accumulo Data Store.
 *
 */
public class AccumuloOptions implements
		DataStoreOptions
{
	@ParametersDelegate
	protected BaseDataStoreOptions baseOptions = new BaseDataStoreOptions();

	@Parameter(names = "--useLocalityGroups", hidden = true, arity = 1)
	protected boolean useLocalityGroups = true;

	@Parameter(names = "--enableBlockCache", hidden = true, arity = 1)
	protected boolean enableBlockCache = true;

	@Override
	public boolean isPersistAdapter() {
		return baseOptions.isPersistAdapter();
	}

	@Override
	public boolean isPersistIndex() {
		return baseOptions.isPersistIndex();
	}

	@Override
	public boolean isPersistDataStatistics() {
		return baseOptions.isPersistDataStatistics();
	}

	@Override
	public boolean isUseAltIndex() {
		return baseOptions.isUseAltIndex();
	}

	@Override
	public boolean isCreateTable() {
		return baseOptions.isCreateTable();
	}

	public void setPersistAdapter(
			final boolean persistAdapter ) {
		baseOptions.setPersistAdapter(
				persistAdapter);
	}

	public void setPersistIndex(
			final boolean persistIndex ) {
		baseOptions.setPersistIndex(
				persistIndex);
	}

	public void setPersistDataStatistics(
			final boolean persistDataStatistics ) {
		baseOptions.setPersistDataStatistics(
				persistDataStatistics);
	}

	public void setUseAltIndex(
			final boolean useAltIndex ) {
		baseOptions.setUseAltIndex(
				useAltIndex);
	}

	public void setCreateTable(
			final boolean createTable ) {
		baseOptions.setCreateTable(
				createTable);
	}

	public boolean isUseLocalityGroups() {
		return useLocalityGroups;
	}

	public void setUseLocalityGroups(
			final boolean useLocalityGroups ) {
		this.useLocalityGroups = useLocalityGroups;
	}

	public boolean isEnableBlockCache() {
		return enableBlockCache;
	}

	public void setEnableBlockCache(
			final boolean enableBlockCache ) {
		this.enableBlockCache = enableBlockCache;
	}
}

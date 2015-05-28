package mil.nga.giat.geowave.core.ingest;

import java.util.List;

import mil.nga.giat.geowave.core.cli.DataStoreCommandLineOptions;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

/**
 * This simply executes an operation to clear a given namespace. It will delete
 * all tables prefixed by the given namespace.
 */
public class ClearNamespaceDriver extends
		AbstractIngestCommandLineDriver
{
	private final static Logger LOGGER = Logger.getLogger(ClearNamespaceDriver.class);
	protected DataStoreCommandLineOptions dataStoreOptions;
	protected IngestCommandLineOptions ingest;

	public ClearNamespaceDriver(
			final String operation ) {
		super(
				operation);
	}

	@Override
	public void parseOptionsInternal(
			final CommandLine commandLine )
			throws ParseException {
		dataStoreOptions = DataStoreCommandLineOptions.parseOptions(commandLine);
		ingest = IngestCommandLineOptions.parseOptions(commandLine);
	}

	@Override
	public void applyOptionsInternal(
			final Options allOptions ) {
		DataStoreCommandLineOptions.applyOptions(allOptions);
		IngestCommandLineOptions.applyOptions(allOptions);
	}

	@Override
	protected void runInternal(
			final String[] args,
			final List<IngestFormatPluginProviderSpi<?, ?>> pluginProviders ) {
		// just check if the flag to clear namespaces is set, and even if it is
		// not, clear it, but only if a namespace is provided
		if (!ingest.isClearNamespace()) {
			clearNamespace();
		}
	}

	protected void clearNamespace() {
		// don't delete all tables in the case that no namespace is given
		if ((ingest.getNamespace() != null) && !ingest.getNamespace().isEmpty()) {
			LOGGER.info("deleting everything in namespace '" + ingest.getNamespace() + "'");
			dataStoreOptions.createStore(
					ingest.getNamespace()).delete(
					null);
		}
		else {
			LOGGER.error("cannot clear a namespace if no namespace is provided");
		}
	}
}

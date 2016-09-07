package mil.nga.giat.geowave.adapter.vector.index;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import mil.nga.giat.geowave.core.store.index.SecondaryIndexType;

import org.codehaus.jackson.annotate.JsonIgnore;

public class NumericSecondaryIndexConfiguration extends
		AbstractSecondaryIndexConfiguration<Number>
{

	private static final long serialVersionUID = -8471135802818658951L;
	public static final String INDEX_KEY = "2ND_IDX_NUMERIC";

	public NumericSecondaryIndexConfiguration() {
		super(
				Number.class,
				Collections.<String> emptySet(),
				SecondaryIndexType.JOIN);
	}

	public NumericSecondaryIndexConfiguration(
			final String attribute,
			final SecondaryIndexType secondaryIndexType ) {
		super(
				Number.class,
				attribute,
				secondaryIndexType);
	}

	public NumericSecondaryIndexConfiguration(
			final Set<String> attributes,
			final SecondaryIndexType secondaryIndexType ) {
		super(
				Number.class,
				attributes,
				secondaryIndexType);
	}

	public NumericSecondaryIndexConfiguration(
			final Class<Number> clazz,
			final Set<String> attributes,
			final SecondaryIndexType secondaryIndexType,
			final List<String> fieldIds ) {
		super(
				clazz,
				attributes,
				secondaryIndexType,
				fieldIds);
	}

	public NumericSecondaryIndexConfiguration(
			Class<Number> clazz,
			Set<String> attributes,
			SecondaryIndexType secondaryIndexType ) {
		super(
				clazz,
				attributes,
				secondaryIndexType);
		// TODO Auto-generated constructor stub
	}

	public NumericSecondaryIndexConfiguration(
			Class<Number> clazz,
			String attribute,
			SecondaryIndexType secondaryIndexType ) {
		super(
				clazz,
				attribute,
				secondaryIndexType);
		// TODO Auto-generated constructor stub
	}

	@JsonIgnore
	@Override
	public String getIndexKey() {
		return INDEX_KEY;
	}

}

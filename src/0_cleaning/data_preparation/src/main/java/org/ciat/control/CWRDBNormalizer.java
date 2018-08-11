package org.ciat.control;


import org.ciat.model.Basis;
import org.ciat.model.DataSourceName;
import org.ciat.model.TaxonFinder;
import org.ciat.model.Utils;

public class CWRDBNormalizer extends Normalizer {

	@Override
	public String getDecimalLatitude() {
		return values[colIndex.get("final_lat")];
	}

	@Override
	public String getDecimalLongitude() {
		return values[colIndex.get("final_lon")];
	}

	@Override
	public String getCountry() {
		String country = values[colIndex.get("final_iso2")];
		country = Utils.iso2CountryCodeToIso3CountryCode(country);
		return country;
	}

	@Override
	public boolean isUseful() {
		
		boolean returnSuper = super.isUseful();
		
		if(returnSuper == false){
			return returnSuper;
		}

		if (!(values[colIndex.get("coord_source")].equals("original")
				|| values[colIndex.get("coord_source")].equals("georef"))) {
			return false;
		}

		if (!(values[colIndex.get("source")].equals("G") || values[colIndex.get("source")].equals("H"))) {
			return false;
		}

		return true;

	}

	@Override
	public DataSourceName getDataSourceName() {
		return DataSourceName.CWRDB;
	}

	@Override
	public Basis getBasis() {
		if (values[colIndex.get("source")].toUpperCase().equals("G")) {
			return Basis.G;
		}
		return Basis.H;
	}

	@Override
	public String getYear() {
		String year = values[colIndex.get("colldate")];
		return Utils.validateYear(year);
	}

	@Override
	public String getTaxonkey() {
		return TaxonFinder.getInstance().fetchTaxonInfo(values[colIndex.get("taxon_final")]);
	}

	@Override
	public String getSpecificSeparator() {
		return "\\|";
	}

}
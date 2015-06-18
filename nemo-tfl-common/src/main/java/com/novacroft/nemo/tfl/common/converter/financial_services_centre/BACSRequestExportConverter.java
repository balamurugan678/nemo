package com.novacroft.nemo.tfl.common.converter.financial_services_centre;

import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSRequestExportDTO;

public interface BACSRequestExportConverter {
	 String[] convert(BACSRequestExportDTO bacsRequestExportDTO);
}

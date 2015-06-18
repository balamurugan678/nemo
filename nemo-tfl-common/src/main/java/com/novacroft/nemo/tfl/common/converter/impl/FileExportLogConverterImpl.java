package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.FileExportLog;
import com.novacroft.nemo.tfl.common.transfer.FileExportLogDTO;
import org.springframework.stereotype.Component;

/**
 * Convert between file export log entity and DTO.
 */
@Component(value = "fileExportLogConverter")
public class FileExportLogConverterImpl extends BaseDtoEntityConverterImpl<FileExportLog, FileExportLogDTO> {
    @Override
    protected FileExportLogDTO getNewDto() {
        return new FileExportLogDTO();
    }
}

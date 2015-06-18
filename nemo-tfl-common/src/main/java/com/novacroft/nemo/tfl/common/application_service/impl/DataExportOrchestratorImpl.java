package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.constant.DateConstant.SHORT_DATE_PATTERN;
import static com.novacroft.nemo.common.constant.DateConstant.YYYYMMDDHHMMSS;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.HOTLISTFILEEXTENSION;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.HOTLISTFILELOCATION;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.common.utils.OysterCardNumberUtil;
import com.novacroft.nemo.tfl.common.application_service.DataExportOrchestrator;
import com.novacroft.nemo.tfl.common.constant.HotlistedCardExportStatus;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;
import com.novacroft.nemo.tfl.common.util.JobLogUtil;

/**
 * Class that creates the hot list reason file
 */
@Service(value = "dataExportOrchestrator")
public class DataExportOrchestratorImpl implements DataExportOrchestrator {

    private static final String DATA_HEADER_SUFFIX = ",HRF,CARD";
    private static final String DATA_HEADER_PREFIX = "WEB-01,WEB,";
    private static final String TFL_TERMINATE_FILE_STRING = "END";
    private static final String TFL_FILENAME_PREFIX = "WEB-01_";
    private static final String TFL_FILENAME_SUFFIX = "_hrf";
    private static final String ERROR_MESSAGE = "could not be loaded from the database";
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String ACTION = "DC";
    private static final String PRIORITY = "3";
    private static final String AUTOMATIC_REMOVAL = "Y";

    public static final String SEPARATOR = ",";

    static final Logger logger = LoggerFactory.getLogger(DataExportOrchestratorImpl.class);

    @Autowired
    protected CardDataService cardDataService;

    @Autowired
    protected SystemParameterService systemParameterService;

    protected String hotlistFilename;
    protected OutputStreamWriter outputStreamWriter;
    protected Writer fileWriter;
    protected FileOutputStream fileOutStream;

    @Override
    public void exportPrestige(JobLogDTO jobLog) {

        String hotlistFileLocation = systemParameterService.getParameterValue(HOTLISTFILELOCATION.code());
        String hotlistFileExtension = systemParameterService.getParameterValue(HOTLISTFILEEXTENSION.code());

        if (hotlistFileExtension == null || hotlistFileExtension.length() < 1) {
            throw new IllegalArgumentException(HOTLISTFILEEXTENSION.name() + ERROR_MESSAGE);
        }
        if (hotlistFileLocation == null || hotlistFileLocation.length() < 1) {
            throw new IllegalArgumentException(HOTLISTFILELOCATION.name() + ERROR_MESSAGE);
        }

        Date now = new java.util.Date();
        hotlistFilename = TFL_FILENAME_PREFIX + DateUtil.formatDate(now, YYYYMMDDHHMMSS) + TFL_FILENAME_SUFFIX;

        try {
            List<CardDTO> hotlistedCards = cardDataService.findHotlistedCardsWithReason();

            File hotlistedRequestFile = getfile(hotlistFileLocation, hotlistFileExtension);
            if (!hotlistedRequestFile.exists()) {
                hotlistedRequestFile.createNewFile();
            }
            JobLogUtil.logMessage(jobLog, "Hotlist card request file: " + hotlistedRequestFile.getName());
            fileOutStream = getfileOutStream(hotlistedRequestFile);
            outputStreamWriter = getOutputStreamWriter(fileOutStream);
            fileWriter = getfileWriter(outputStreamWriter);

            final String fileDate = DateUtil.formatDate(now, SHORT_DATE_PATTERN);
            fileWriter.write(buildFileHeader(fileDate));
            fileWriter.write(LINE_SEPARATOR);
            StringBuilder sb = new StringBuilder();
            for (CardDTO card : hotlistedCards) {
                if (card != null && HotlistedCardExportStatus.HOTLIST_STATUS_READYTOEXPORT.getCode().equals(card.getHotlistStatus())) {
                    String oysterCardNo = card.getCardNumber();
                    final String hotlistcode = String.valueOf(card.getHotlistReason().getId());
                    oysterCardNo = OysterCardNumberUtil.getFullCardNumber(oysterCardNo);
                    sb.append(oysterCardNo);
                    sb.append(SEPARATOR);
                    sb.append(ACTION);
                    sb.append(SEPARATOR);
                    sb.append(hotlistcode);
                    sb.append(SEPARATOR);
                    sb.append(card.getHotlistReason().getDescription());
                    sb.append(SEPARATOR);
                    sb.append(PRIORITY);
                    sb.append(SEPARATOR);
                    sb.append(AUTOMATIC_REMOVAL);
                    sb.append(LINE_SEPARATOR);
                    card.setHotlistStatus(HotlistedCardExportStatus.HOTLIST_STATUS_EXPORTED.getCode());
                }
            }
            fileWriter.write(sb.toString());
            fileWriter.write(TFL_TERMINATE_FILE_STRING);
            fileWriter.close();
            cardDataService.createOrUpdateAll(hotlistedCards);

        } catch (IOException e) {
            logger.error("Problem writing to the file " + hotlistFileLocation + "/" + hotlistFilename + hotlistFileExtension);
        }
    }

    private String buildFileHeader(final String fileDate) {
        return DATA_HEADER_PREFIX + fileDate + DATA_HEADER_SUFFIX;
    }

    protected File getfile(String hotlistFileLocation, String hotlistFileExtension) {
        return new File(hotlistFileLocation + "/" + hotlistFilename + hotlistFileExtension);
    }

    protected BufferedWriter getfileWriter(OutputStreamWriter outputStreamWriter) {
        return new BufferedWriter(outputStreamWriter);
    }

    protected OutputStreamWriter getOutputStreamWriter(FileOutputStream fileOutStream) {
        return new OutputStreamWriter(fileOutStream);
    }

    protected FileOutputStream getfileOutStream(File statText) throws FileNotFoundException {
        return new FileOutputStream(statText);
    }

}

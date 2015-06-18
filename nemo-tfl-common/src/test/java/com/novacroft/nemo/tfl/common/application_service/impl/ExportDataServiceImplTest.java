package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.HOTLISTFILEEXTENSION;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.HOTLISTFILELOCATION;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;

public class ExportDataServiceImplTest {

    private static final String TEST_FILE_OUTPUT_LOCATION = System.getProperty("java.io.tmpdir");
    private static final String TEST_FILE_EXTENSION = ".tmp";
    private CardDataService cardDataService;
    private SystemParameterService systemParameterService;
    public DataExportOrchestratorImpl exportDataService;
    private OutputStreamWriter mockOutputStreamWriter;
    private BufferedWriter mockFileWriter;
    private FileOutputStream mockFileOutStream;
    private CardDTO mockCard;
    private List<CardDTO> cardList;

    @Before
    public void setup() {
        exportDataService = new DataExportOrchestratorImpl();

        cardDataService = mock(CardDataService.class);
        systemParameterService = mock(SystemParameterService.class);
        mockOutputStreamWriter = mock(OutputStreamWriter.class);
        mockFileWriter = mock(BufferedWriter.class);
        mockFileOutStream = mock(FileOutputStream.class);
        mockCard = mock(CardDTO.class);
        cardList = new ArrayList<CardDTO>();
        cardList.add(mockCard);

        exportDataService.cardDataService = cardDataService;
        exportDataService.systemParameterService = systemParameterService;
        exportDataService.fileOutStream = mockFileOutStream;
        exportDataService.fileWriter = mockFileWriter;
        exportDataService.outputStreamWriter = mockOutputStreamWriter;

    }

    @Test
    public void exportPrestige() throws Exception {

        when(systemParameterService.getParameterValue(HOTLISTFILELOCATION.code())).thenReturn(TEST_FILE_OUTPUT_LOCATION);
        when(systemParameterService.getParameterValue(HOTLISTFILEEXTENSION.code())).thenReturn(TEST_FILE_EXTENSION);

        when(cardDataService.findHotlistedCards()).thenReturn(CardTestUtil.getTestCards1And2());

        JobLogDTO jobLogDTO = new JobLogDTO();

        exportDataService.exportPrestige(jobLogDTO);

    }

    @Test(expected = IllegalArgumentException.class)
    public void exportPrestigeFail() throws Exception {

        when(systemParameterService.getParameterValue(anyString())).thenReturn(null);
        exportDataService.exportPrestige(null);

    }

    @Test
    public void exportPrestigePassWithCardData() throws Exception {
        File mockFile = mock(File.class);
        DataExportOrchestratorImpl mockExportDataService = mock(DataExportOrchestratorImpl.class);

        when(systemParameterService.getParameterValue(HOTLISTFILELOCATION.code())).thenReturn(TEST_FILE_OUTPUT_LOCATION);
        when(systemParameterService.getParameterValue(HOTLISTFILEEXTENSION.code())).thenReturn(TEST_FILE_EXTENSION);
        when(cardDataService.findHotlistedCardsWithReason()).thenReturn(CardTestUtil.getTestHotlistedCards1And2());
        when(cardDataService.createOrUpdateAll(cardList)).thenReturn(CardTestUtil.getTestHotlistedCards1And2());
        when(mockExportDataService.getfile(TEST_FILE_OUTPUT_LOCATION, TEST_FILE_EXTENSION)).thenReturn(mockFile);
        when(mockExportDataService.getfileOutStream(mockFile)).thenReturn(mockFileOutStream);
        when(mockExportDataService.getOutputStreamWriter(mockFileOutStream)).thenReturn(mockOutputStreamWriter);
        when(mockExportDataService.getfileWriter(mockOutputStreamWriter)).thenReturn(mockFileWriter);

        JobLogDTO jobLogDTO = new JobLogDTO();
        exportDataService.exportPrestige(jobLogDTO);

    }

    @After
    public void breakDown() {

        final File folder = new File(TEST_FILE_OUTPUT_LOCATION);
        final File[] files = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(final File dir, final String name) {
                return name.matches("WEB-01.*\\" + TEST_FILE_EXTENSION);
            }
        });
        for (final File file : files) {
            if (!file.delete()) {
                System.err.println("Can't remove " + file.getAbsolutePath());
            }
        }

    }
}

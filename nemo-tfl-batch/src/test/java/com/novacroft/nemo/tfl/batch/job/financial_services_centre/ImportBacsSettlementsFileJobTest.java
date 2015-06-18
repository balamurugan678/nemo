package com.novacroft.nemo.tfl.batch.job.financial_services_centre;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.FileFilter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.validation.Validator;

import com.novacroft.nemo.tfl.batch.converter.ImportRecordConverter;
import com.novacroft.nemo.tfl.batch.dispatcher.ImportRecordDispatcher;
public class ImportBacsSettlementsFileJobTest {

	private ImportBacsSettlementsFileJob job;
	
	 private FileFilter mockFileFilter;
	 private ImportRecordDispatcher mockImportRecordDispatcher;
	 private Validator mockValidator;
	 private ImportRecordConverter mockImportRecordConverter;
	
	
	@Before
	public void setUp(){
	
		this.mockFileFilter = mock(FileFilter.class);
	    this.mockImportRecordDispatcher = mock(ImportRecordDispatcher.class);
	    this.mockValidator = mock(Validator.class);
	    this.mockImportRecordConverter = mock(ImportRecordConverter.class);
	    
	    this.job = Mockito.spy(new ImportBacsSettlementsFileJob());
	    this.job.bacsSettlementsFileFilter = this.mockFileFilter;
	    this.job.bacsPaymentSettlementDispatcher = this.mockImportRecordDispatcher;
	    this.job.bacsPaymentSettlementValidator = this.mockValidator;
	    this.job.bacsSettlementRecordConverter = this.mockImportRecordConverter;
		
	}
	
	@Test
	public void shouldReturnConverter() {
		assertEquals(this.mockImportRecordConverter, this.job.getImportRecordConverter());
	}
	
	@Test
	public void shouldReturnValidator() {
		assertEquals(this.mockValidator, this.job.getImportRecordValidator());
	}
	
	@Test
	public void shouldReturnFileFilter() {
		assertEquals(this.mockFileFilter, this.job.getImportFileFilter());
	}
	
	@Test
	public void shouldreturnDispatcher() {
		assertEquals(this.mockImportRecordDispatcher, this.job.getImportRecordDispatcher());
	}
}

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

public class ImportBacsFailureFileJobTest {
	
	private ImportBacsFailureFileJob job;
	
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
	    
	    this.job = Mockito.spy(new ImportBacsFailureFileJob());
	    this.job.bacsFailuresFileFilter = this.mockFileFilter;
	    this.job.bacsPaymentFailedDispatcher = this.mockImportRecordDispatcher;
	    this.job.bacsFailureValidator= this.mockValidator;
	    this.job.bacsFailedRecordConverter = this.mockImportRecordConverter;
		
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

package com.novacroft.nemo.common.data_service;

import com.novacroft.nemo.common.converter.DtoEntityConverter;
import com.novacroft.nemo.common.data_access.BaseDAO;
import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.common.domain.AbstractBaseEntity;
import com.novacroft.nemo.common.domain.BaseEntity;
import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.common.transfer.AbstractBaseDTO;
import com.novacroft.nemo.common.transfer.BaseDTO;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Base data service unit tests.
 */
public class BaseDataServiceTest {
    private static final Long TEST_ID_1 = 4L;
    private static final Long TEST_ID_2 = 8L;
    private static final String TEST_USER_NAME = "test-user-name";
    private BaseEntity mockEntity1;
    private BaseEntity mockEntity2;
    private BaseDTO mockDto1;
    private BaseDTO mockDto2;
    private BaseDAO<BaseEntity> mockDao;
    private DtoEntityConverter<BaseEntity, BaseDTO> mockConverter;
    private BaseDataService<BaseEntity, BaseDTO> dataService;
    private NemoUserContext mockNemoUserContext;
    private List<BaseEntity> entities;

    @Before
    public void setUp() {
        this.dataService = mock(BaseDataServiceImpl.class);
        when(this.dataService.findById(anyLong())).thenCallRealMethod();
        when(this.dataService.findByExternalId(anyLong())).thenCallRealMethod();
        when(this.dataService.findAll()).thenCallRealMethod();
        when(this.dataService.createOrUpdate(any(BaseDTO.class))).thenCallRealMethod();
        when(dataService.getInternalIdFromExternalId(anyLong())).thenCallRealMethod();
        doCallRealMethod().when(this.dataService).delete(any(BaseDTO.class));
        doCallRealMethod().when(this.dataService).setConverter(any(DtoEntityConverter.class));
        doCallRealMethod().when(this.dataService).setDao(any(BaseDAO.class));

        this.mockEntity1 = mock(AbstractBaseEntity.class, CALLS_REAL_METHODS);
        this.mockEntity2 = mock(AbstractBaseEntity.class, CALLS_REAL_METHODS);
        this.mockDto1 = mock(AbstractBaseDTO.class, CALLS_REAL_METHODS);
        this.mockDto2 = mock(AbstractBaseDTO.class, CALLS_REAL_METHODS);
        this.mockConverter = mock(DtoEntityConverter.class);
        this.mockDao = mock(BaseDAOImpl.class);
        this.mockNemoUserContext = mock(NemoUserContext.class);

        this.dataService.setConverter(this.mockConverter);
        this.dataService.setDao(this.mockDao);
        ReflectionTestUtils.setField(this.dataService, "nemoUserContext", this.mockNemoUserContext);

        when(this.mockNemoUserContext.getUserName()).thenReturn(TEST_USER_NAME);
        when(this.mockConverter.convertEntityToDto(eq(this.mockEntity1))).thenReturn(this.mockDto1);
        when(this.mockConverter.convertEntityToDto(eq(this.mockEntity2))).thenReturn(this.mockDto2);
        this.mockEntity1.setId(TEST_ID_1);
        this.mockEntity2.setId(TEST_ID_2);
        this.mockDto1.setId(TEST_ID_1);
        this.mockDto2.setId(TEST_ID_2);

        this.entities = new ArrayList<BaseEntity>();
        this.entities.add(this.mockEntity1);
        this.entities.add(this.mockEntity2);
    }

    @Test
    public void shouldFindById() {
        when(this.mockDao.findById(anyLong())).thenReturn(this.mockEntity1);
        BaseDTO result = this.dataService.findById(TEST_ID_1);
        assertEquals(this.mockDto1, result);
    }

    @Test
    public void shouldFindAll() {
        when(this.mockDao.findAll()).thenReturn(this.entities);
        List result = this.dataService.findAll();
        assertEquals(2, result.size());
        assertTrue(result.contains(this.mockDto1));
        assertTrue(result.contains(this.mockDto2));
    }

    @Test
    public void shouldUpdate() {
        when(this.mockDao.findById(anyLong())).thenReturn(this.mockEntity1);
        when(this.mockDao.createOrUpdate(any(BaseEntity.class))).thenReturn(this.mockEntity1);
        when(this.mockConverter.convertDtoToEntity(any(BaseDTO.class), any(BaseEntity.class))).thenReturn(this.mockEntity1);
        BaseDTO result = this.dataService.createOrUpdate(this.mockDto1);
        assertEquals(this.mockDto1, result);
        verify(this.mockEntity1, never()).setCreatedDateTime(any(Date.class));
        verify(this.mockEntity1, never()).setCreatedUserId(anyString());
        verify(this.mockEntity1).setModifiedDateTime(any(Date.class));
        verify(this.mockEntity1).setModifiedUserId(anyString());
    }

    @Test
    public void shouldCreate() {
        this.mockDto1.setId(null);
        when(this.dataService.getNewEntity()).thenReturn(this.mockEntity1);
        when(this.mockDao.createOrUpdate(any(BaseEntity.class))).thenReturn(this.mockEntity1);
        when(this.mockConverter.convertDtoToEntity(any(BaseDTO.class), any(BaseEntity.class))).thenReturn(this.mockEntity1);
        BaseDTO result = this.dataService.createOrUpdate(this.mockDto1);
        assertEquals(this.mockDto1, result);
        verify(this.mockEntity1).setCreatedDateTime(any(Date.class));
        verify(this.mockEntity1).setCreatedUserId(anyString());
        verify(this.mockEntity1, never()).setModifiedDateTime(any(Date.class));
        verify(this.mockEntity1, never()).setModifiedUserId(anyString());
    }

    @Test
    public void shouldDelete() {
        when(this.mockDao.findById(anyLong())).thenReturn(this.mockEntity1);
        doNothing().when(this.mockDao).delete(any(BaseEntity.class));
        this.dataService.delete(this.mockDto1);
        verify(this.mockDao).findById(anyLong());
        verify(this.mockDao).delete(any(BaseEntity.class));
    }

    @Test
    public void shouldFindByExternalId() {
        when(this.mockDao.findByExternalId(anyLong())).thenReturn(this.mockEntity1);
        BaseDTO result = this.dataService.findByExternalId(TEST_ID_1);
        assertEquals(this.mockDto1, result);
    }

    @Test
    public void getInternalIdFromExternalId() {
        when(this.mockDao.findByExternalId(anyLong())).thenReturn(this.mockEntity1);
        when(mockEntity1.getId()).thenReturn(TEST_ID_1);
        when(mockDao.getInternalIdFromExternalId(anyLong())).thenReturn(TEST_ID_1);
        Long result = this.dataService.getInternalIdFromExternalId(TEST_ID_2);
        assertEquals(TEST_ID_1, result);
    }

    
    @Test
    public void testAddLike(){
        BaseDataServiceImpl<BaseEntity, BaseDTO> baseDataService = getNewImplementationOfBaseData();
        assertEquals("Test", baseDataService.addLike("Test", true));
        assertNull(baseDataService.getNewEntity());
    }
    
    @Test
    public void testDoNotAddLike(){
        BaseDataServiceImpl<BaseEntity, BaseDTO> baseDataService = getNewImplementationOfBaseData();
        assertEquals("Test%", baseDataService.addLike("Test", false));
        assertNull(baseDataService.getNewEntity());
    }
    
    @Test
    public void testGetPeriodClauseIfEffectiveDateIsNull(){
        BaseDataServiceImpl<BaseEntity, BaseDTO> baseDataService = getNewImplementationOfBaseData();
        String periodClause = baseDataService.getPeriodClause(null, "p");
        assertNotNull(periodClause);
        assertTrue(periodClause.contains("p.effectiveFrom <= CURRENT_DATE()"));
    }
    
    @Test
    public void testGetPeriodClauseIfEffectiveDateIsNotNull(){
        BaseDataServiceImpl<BaseEntity, BaseDTO> baseDataService = getNewImplementationOfBaseData();
        String periodClause = baseDataService.getPeriodClause(new Date(), "p");
        assertNotNull(periodClause);
        assertFalse(periodClause.contains("p.effectiveFrom <= CURRENT_DATE()"));
        assertTrue(periodClause.contains("p.effectiveFrom <= :effectiveDate"));
    }
    
    private BaseDataServiceImpl<BaseEntity, BaseDTO> getNewImplementationOfBaseData(){
        return new BaseDataServiceImpl<BaseEntity, BaseDTO>() {

            @Override
            public BaseEntity getNewEntity() {
                return null;
            }
        };
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testCreateOrUpdateAll(){
        List<BaseDTO> dtos = new ArrayList<>();
        dtos.add(mockDto1);
        when(this.mockDao.createOrUpdate(any(BaseEntity.class))).thenReturn(this.mockEntity1);
        when(this.mockConverter.convertDtoToEntity(any(BaseDTO.class), any(BaseEntity.class))).thenReturn(this.mockEntity1);
        when(dataService.createOrUpdateAll(any(List.class))).thenCallRealMethod();
        List<BaseDTO> createdOrUpdatedDTOs = dataService.createOrUpdateAll(dtos);
        assertNotNull(createdOrUpdatedDTOs);
        assertTrue(createdOrUpdatedDTOs.size() > 0);
        
    }
    
}

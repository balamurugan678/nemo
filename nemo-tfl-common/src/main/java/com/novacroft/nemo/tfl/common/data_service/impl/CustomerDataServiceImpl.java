package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.tfl.common.util.CustomerSearchUtil.CARD_NUMBER_PARAMETER_NAME;
import static com.novacroft.nemo.tfl.common.util.CustomerSearchUtil.CUSTOMER_ID_PARAMETER_NAME;
import static com.novacroft.nemo.tfl.common.util.CustomerSearchUtil.EMAIL_PARAMETER_NAME;
import static com.novacroft.nemo.tfl.common.util.CustomerSearchUtil.FIRST_NAME_METAPHONE_PARAMETER_NAME;
import static com.novacroft.nemo.tfl.common.util.CustomerSearchUtil.FIRST_NAME_PARAMETER_NAME;
import static com.novacroft.nemo.tfl.common.util.CustomerSearchUtil.LAST_NAME_METAPHONE_PARAMETER_NAME;
import static com.novacroft.nemo.tfl.common.util.CustomerSearchUtil.LAST_NAME_PARAMETER_NAME;
import static com.novacroft.nemo.tfl.common.util.CustomerSearchUtil.POSTCODE_PARAMETER_NAME;
import static com.novacroft.nemo.tfl.common.util.CustomerSearchUtil.USERNAME_PARAMETER_NAME;
import static com.novacroft.nemo.tfl.common.util.CustomerSearchUtil.isCardNumberNotNull;
import static com.novacroft.nemo.tfl.common.util.CustomerSearchUtil.isCustomerIdNotNull;
import static com.novacroft.nemo.tfl.common.util.CustomerSearchUtil.isEmailNotNull;
import static com.novacroft.nemo.tfl.common.util.CustomerSearchUtil.isExactMatchAndFirstNameNotNull;
import static com.novacroft.nemo.tfl.common.util.CustomerSearchUtil.isExactMatchAndLastNameNotNull;
import static com.novacroft.nemo.tfl.common.util.CustomerSearchUtil.isFirstNameNotNull;
import static com.novacroft.nemo.tfl.common.util.CustomerSearchUtil.isLastNameNotNull;
import static com.novacroft.nemo.tfl.common.util.CustomerSearchUtil.isNotExactMatch;
import static com.novacroft.nemo.tfl.common.util.CustomerSearchUtil.isNotExactMatchAndFirstNameNotNull;
import static com.novacroft.nemo.tfl.common.util.CustomerSearchUtil.isNotExactMatchAndLastNameNotNull;
import static com.novacroft.nemo.tfl.common.util.CustomerSearchUtil.isPostcodeNotNull;
import static com.novacroft.nemo.tfl.common.util.CustomerSearchUtil.isUserNameNotNull;
import static com.novacroft.nemo.tfl.common.util.CustomerSearchUtil.stripSpaces;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.common.support.QueryParameterBuilder;
import com.novacroft.nemo.common.support.QuerySqlBuilder;
import com.novacroft.nemo.common.utils.MetaphoneUtil;
import com.novacroft.nemo.tfl.common.converter.CustomerSearchConverter;
import com.novacroft.nemo.tfl.common.converter.impl.CustomerConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.CustomerDAO;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.domain.Customer;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerSearchArgumentsDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerSearchResultDTO;

/**
 * Content data service implementation
 */
@Service(value = "customerDataService")
@Transactional(readOnly = true)
public class CustomerDataServiceImpl extends BaseDataServiceImpl<Customer, CustomerDTO> implements CustomerDataService {
    static final Logger logger = LoggerFactory.getLogger(CustomerDataServiceImpl.class);

    private static final String HQL_FROM_CUSTOMER_STRING = "from Customer cu where cu.firstName ";
    private static final String LIKE_STRING = "like";
    private static final String QUOTE_STRING_WITH_SPACE = " ? ";

    @Autowired
    protected CustomerSearchConverter customerSearchConverter;

    @Autowired
    public void setDao(CustomerDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(CustomerConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public Customer getNewEntity() {
        return new Customer();
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDTO findById(long id) {
        return super.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public CustomerDTO createOrUpdate(CustomerDTO dto) {
        dto.setFirstNameMetaphone(MetaphoneUtil.encode(dto.getFirstName()));
        dto.setLastNameMetaphone(MetaphoneUtil.encode(dto.getLastName()));
        return super.createOrUpdate(dto);
    }

    @Override
    public List<CustomerDTO> findByFirstNameAndLastName(String firstName, String lastName, boolean exact) {
        final String hsql =
                HQL_FROM_CUSTOMER_STRING + (exact ? "=" : LIKE_STRING) + " ? and  cu.lastName " + (exact ? "=" : LIKE_STRING) +
                        QUOTE_STRING_WITH_SPACE;
        firstName = (exact ? firstName : firstName + "%");
        return runFindByQuery(hsql, addLike(firstName, exact), addLike(lastName, exact));
    }

    @Override
    public List<CustomerDTO> findByFirstName(String firstName, boolean exact) {
        final String hsql = HQL_FROM_CUSTOMER_STRING + (exact ? "=" : LIKE_STRING) + QUOTE_STRING_WITH_SPACE;
        return runFindByQuery(hsql, addLike(firstName, exact));
    }

    @Override
    public List<CustomerDTO> findByLastName(String lastName, boolean exact) {
        final String hsql = "from Customer cu where cu.lastName " + (exact ? "=" : LIKE_STRING) + QUOTE_STRING_WITH_SPACE;
        return runFindByQuery(hsql, addLike(lastName, exact));
    }

    @SuppressWarnings("unchecked")
    protected List<CustomerDTO> runFindByQuery(String hsql, Object... args) {
        List<Customer> results = dao.findByQuery(hsql, args);
        return convertListToDTO(results);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CustomerDTO> findByFirstNameAndLastName(String firstName, String lastName, boolean exact, int startCount,
                                                        int endCount) {
        final String hsql =
                HQL_FROM_CUSTOMER_STRING + (exact ? "=" : LIKE_STRING) + " ? and  cu.lastName " + (exact ? "=" : LIKE_STRING) +
                        QUOTE_STRING_WITH_SPACE;
        List<Customer> results =
                dao.findByQueryWithLimit(hsql, startCount, endCount, addLike(firstName, exact), addLike(lastName, exact));
        return convertListToDTO(results);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CustomerDTO> findByFirstName(String firstName, boolean exact, int startCount, int endCount) {
        final String hsql = HQL_FROM_CUSTOMER_STRING + (exact ? "=" : LIKE_STRING) + QUOTE_STRING_WITH_SPACE;
        List<Customer> results = dao.findByQueryWithLimit(hsql, startCount, endCount, addLike(firstName, exact));
        return convertListToDTO(results);

    }

    public List<CustomerDTO> convertListToDTO(List<Customer> entities) {
        List<CustomerDTO> resultsDTO = new ArrayList<CustomerDTO>();
        if (entities.iterator().hasNext()) {
            for (Customer c : entities) {
                resultsDTO.add(this.converter.convertEntityToDto(c));
            }
        }
        return resultsDTO;
    }

    @Override
    public List<CustomerSearchResultDTO> findByCriteria(CustomerSearchArgumentsDTO arguments) {
        QuerySqlBuilder querySqlBuilder =
                new QuerySqlBuilder().appendFirstSelect("cu.id").appendSelect("cu.firstname").appendSelect("cu.lastname")
                        .appendSelect("ca.cardnumber").appendSelect("ca.status").appendSelect("a.housenamenumber")
                        .appendSelect("a.street").appendSelect("a.town").appendSelect("a.county").appendSelect("co.name")
                        .appendSelect("a.postcode").appendFirstTable("card ca").appendTable("customer cu")
                        .appendTable("address a").appendTable("country co").appendFirstWhereJoin("a.id (+) = cu.addressid")
                        .appendAndJoin("ca.customerid (+) = cu.id").appendAndJoin("a.country_id = co.id")
                        .appendAndFilter(isCustomerIdNotNull(arguments), "cu.id", CUSTOMER_ID_PARAMETER_NAME)
                        .appendAndFilter(isCardNumberNotNull(arguments), "ca.cardnumber", CARD_NUMBER_PARAMETER_NAME,
                                isNotExactMatch(arguments))
                        .appendAndFilterWithStartParanthasis(isNotExactMatchAndFirstNameNotNull(arguments), "cu.first_name_metaphone",
                                FIRST_NAME_METAPHONE_PARAMETER_NAME)
                         .appendOrFilter(isNotExactMatchAndFirstNameNotNull(arguments), "lower(cu.firstname)",
                                FIRST_NAME_PARAMETER_NAME, isNotExactMatch(arguments))       
                        .appendAndFilter(isExactMatchAndFirstNameNotNull(arguments), "upper(cu.firstname)",
                                FIRST_NAME_PARAMETER_NAME)
                        .appendAndFilterWithStartParanthasis(isNotExactMatchAndLastNameNotNull(arguments) && !isFirstNameNotNull(arguments), "cu.last_name_metaphone",
                                LAST_NAME_METAPHONE_PARAMETER_NAME)
                        .appendOrFilter(isNotExactMatchAndLastNameNotNull(arguments), "lower(cu.lastname)",
                                LAST_NAME_PARAMETER_NAME, isNotExactMatch(arguments))        
                        .appendAndFilter(isExactMatchAndLastNameNotNull(arguments), "upper(cu.lastname)",
                                LAST_NAME_PARAMETER_NAME)
                        .appendAndFilter(isPostcodeNotNull(arguments), "replace(upper(a.postcode), ' ', '')",
                                POSTCODE_PARAMETER_NAME, isNotExactMatch(arguments))
                        .appendAndFilter(isEmailNotNull(arguments), "lower(cu.emailAddress)", EMAIL_PARAMETER_NAME,
                                isNotExactMatch(arguments))
                        .appendAndFilter(isUserNameNotNull(arguments), "lower(cu.username)", USERNAME_PARAMETER_NAME,
                                isNotExactMatch(arguments))
                         .appendEndParenthesis(isNotExactMatch(arguments) && (isFirstNameNotNull(arguments) || isLastNameNotNull(arguments)), "");
        logger.debug(querySqlBuilder.toString());

        QueryParameterBuilder queryParameterBuilder = new QueryParameterBuilder()
                .addParameter(isCustomerIdNotNull(arguments), CUSTOMER_ID_PARAMETER_NAME, arguments.getCustomerId())
                .addParameter(isCardNumberNotNull(arguments), CARD_NUMBER_PARAMETER_NAME, arguments.getCardNumber())
                .addParameter(isExactMatchAndFirstNameNotNull(arguments), FIRST_NAME_PARAMETER_NAME,
                        arguments.getFirstName().toUpperCase())
                .addParameterSurroundedByWildcard(isNotExactMatchAndFirstNameNotNull(arguments), FIRST_NAME_PARAMETER_NAME,
                        arguments.getFirstName().toLowerCase() , isNotExactMatch(arguments))        
                .addParameter(isNotExactMatchAndFirstNameNotNull(arguments), FIRST_NAME_METAPHONE_PARAMETER_NAME,
                        MetaphoneUtil.encode(arguments.getFirstName()))
                .addParameter(isExactMatchAndLastNameNotNull(arguments), LAST_NAME_PARAMETER_NAME,
                        arguments.getLastName().toUpperCase())
                .addParameterSurroundedByWildcard(isNotExactMatchAndLastNameNotNull(arguments), LAST_NAME_PARAMETER_NAME,
                        arguments.getLastName().toLowerCase() , isNotExactMatch(arguments))        
                .addParameter(isNotExactMatchAndLastNameNotNull(arguments) && !isFirstNameNotNull(arguments), LAST_NAME_METAPHONE_PARAMETER_NAME,
                        MetaphoneUtil.encode(arguments.getLastName()))
                .addParameter(isPostcodeNotNull(arguments), POSTCODE_PARAMETER_NAME, stripSpaces(arguments.getPostcode()),
                        isNotExactMatch(arguments))
                .addParameter(isEmailNotNull(arguments), EMAIL_PARAMETER_NAME, (arguments.getEmail()!=null ? arguments.getEmail().toLowerCase():null),
                        isNotExactMatch(arguments))
                .addParameterSurroundedByWildcard(isUserNameNotNull(arguments), USERNAME_PARAMETER_NAME, (arguments.getUserName()!=null ? arguments.getUserName().toLowerCase():null),
                        isNotExactMatch(arguments));
        logger.debug(queryParameterBuilder.toString());

        return this.customerSearchConverter.convert(
                this.dao.findBySqlQueryWithLimitUsingNamedParameters(querySqlBuilder.toString(), arguments.getFirstResult(),
                        arguments.getMaxResults(), queryParameterBuilder.toMap()));
    }

    @Override
    public CustomerDTO findByCardNumber(String cardNumber) {
        final String hsql = "select cu from Card ca, Customer cu where ca.cardNumber = ? and cu.id = ca.customerId";
        Customer customer = this.dao.findByQueryUniqueResult(hsql, cardNumber);
        return (customer != null) ? this.converter.convertEntityToDto(customer) : null;
    }

    @Override
    public CustomerDTO findByUsernameOrEmail(String usernameOrEmail) {
        final String hsql = "select cu from Customer cu where lower(cu.username) = ? OR lower(cu.emailAddress) = ?";
        Customer customer = this.dao.findByQueryUniqueResult(hsql, usernameOrEmail.toLowerCase().trim(),
                usernameOrEmail.toLowerCase().trim());
        return (customer != null) ? this.converter.convertEntityToDto(customer) : null;
    }

    @Override
    public CustomerDTO findByCardId(Long cardId) {
        final String hsql = "select cu from Card ca, Customer cu" + " where ca.id = ? and cu.id = ca.customerId";
        Customer customer = this.dao.findByQueryUniqueResult(hsql, cardId);
        return (customer != null) ? this.converter.convertEntityToDto(customer) : null;
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public CustomerDTO findByCustomerId(Long customerId) {
        final String hsql = "from Customer cu where cu.id = ?";
        Customer customer = dao.findByQueryUniqueResult(hsql, customerId);
        return (null != customer) ? this.converter.convertEntityToDto(customer) : null;
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public CustomerDTO findByExternalIdAndExternalUserId(Long externalId, Long externalUserId) {
        final String hsql = "from Customer cu where cu.externalId = ? and cu.externalUserId = ?";
        Customer customer = dao.findByQueryUniqueResult(hsql, externalId, externalUserId);
        return (null != customer) ? this.converter.convertEntityToDto(customer) : null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CustomerDTO> findByExternalUserId(Long externalUserId) {
        final String hsql = "from Customer c where c.externalUserId = ?";
        List<Customer> results = dao.findByQuery(hsql, externalUserId);
        return (results != null && results.size() > 0 ? convertListToDTO(results) : null);
    }

    @Override
    public CustomerDTO findByTflMasterId(Long tflMasterId) {
        final String hsql = "from Customer c where c.tflMasterId = ?";
        Customer customer = dao.findByQueryUniqueResult(hsql, tflMasterId);
        return (customer != null) ? this.converter.convertEntityToDto(customer) : null;
    }
}

package com.novacroft.nemo.mock_cubic.converter;

import com.novacroft.nemo.common.converter.XmlModelConverter;
import com.novacroft.nemo.common.domain.cubic.*;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.mock_cubic.constant.Constant;
import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoResponseV2;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * CardInfoResponseV2 converter that includes empty tags for null value elements
 */
@Component("cardInfoResponseV2EmptyTagConverter")
public class CardInfoResponseV2EmptyTagConverterImpl implements XmlModelConverter<CardInfoResponseV2> {

    @Autowired
    protected ApplicationContext applicationContext;
    @Autowired
    protected XmlModelConverter<CardInfoResponseV2> cardInfoResponseV2Converter;

    protected URL castorMappingFileUrl;

    @PostConstruct
    public void initialiseMapping() {
        try {
            this.castorMappingFileUrl = this.applicationContext.getResource(Constant.GET_CARD_MAPPING_WITH_EMPTY_TAGS).getURL();
        } catch (IOException e) {
            throw new ApplicationServiceException(e.getMessage(), e);
        }
    }

    @Override
    public CardInfoResponseV2 convertXmlToModel(String xml) {
        return this.cardInfoResponseV2Converter.convertXmlToModel(xml);
    }

    @Override
    public Object convertXmlToObject(String xml) {
        return this.cardInfoResponseV2Converter.convertXmlToObject(xml);
    }

    @Override
    public String convertModelToXml(CardInfoResponseV2 cardInfoResponseV2) {
        if (cardInfoResponseV2.getHotListReasons() == null) {
            cardInfoResponseV2.setHotListReasons(new HotListReasons());
        }

        if (cardInfoResponseV2.getHolderDetails() == null) {
            cardInfoResponseV2.setHolderDetails(new HolderDetails());
        }

        if (cardInfoResponseV2.getPpvDetails() == null) {
            cardInfoResponseV2.setPpvDetails(new PrePayValue());
        }

        if (cardInfoResponseV2.getPptDetails() == null) {
            cardInfoResponseV2.setPptDetails(new PrePayTicketDetails());
        }

        if (cardInfoResponseV2.getPptDetails().getPptSlots() == null) {
            cardInfoResponseV2.getPptDetails().setPptSlots(new ArrayList<PrePayTicketSlot>());
        }

        if (cardInfoResponseV2.getPendingItems() == null) {
            cardInfoResponseV2.setPendingItems(new PendingItems());
        }

        if (cardInfoResponseV2.getHotlist() == null) {
            cardInfoResponseV2.setHotlist(new ArrayList<String>());
        }

        try {
            Mapping mapping = new Mapping();

            mapping.loadMapping(this.castorMappingFileUrl);

            StringWriter response = new StringWriter();

            Marshaller marshaller = new Marshaller(response);
            marshaller.setMapping(mapping);
            marshaller.setSupressXMLDeclaration(true);
            marshaller.setSuppressNamespaces(true);
            marshaller.setSuppressXSIType(true);
            marshaller.marshal(cardInfoResponseV2);

            return response.toString();
        } catch (IOException | MappingException | MarshalException | ValidationException e) {
            throw new ApplicationServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void setCastorMappingFileUrl(List<URL> castorMappingFileUrl) {
        this.cardInfoResponseV2Converter.setCastorMappingFileUrl(castorMappingFileUrl);
    }
}


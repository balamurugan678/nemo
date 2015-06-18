package com.novacroft.nemo.tfl.innovator.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.novacroft.nemo.common.data_service.ContentDataService;
import com.novacroft.nemo.common.transfer.ContentDTO;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.innovator.command.ContentCreatorCmd;


@Controller
@RequestMapping(value = Page.INV_ADD_CONTENT)
public class AddContentController {
    private static final String LABEL = "label";
    private static final String PLACEHOLDER = "placeholder";
    private static final String TIP= "tip";
    
    @Autowired
    protected ContentDataService contentDataService;
    
    private static final String INSERT_STATEMENT = "\n\nINSERT INTO ${schemaName}.content " +
    "( ID, CREATEDUSERID, CREATEDDATETIME, MODIFIEDUSERID, MODIFIEDDATETIME, CODE, LOCALE, CONTENT) " + 
    "VALUES " +
    " (${schemaName}.content_seq.nextval, 'Installer', SYSDATE, NULL, NULL,'%s','%s','%s' ";
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView addContent(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView(PageView.INV_ADD_CONTENT);
        modelAndView.addObject(PageCommand.CONTENT_CREATOR, new ContentCreatorCmd());
        return modelAndView;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView load(@RequestParam(value = "code", required = true) String code, @RequestParam(value = "locale", required = true) String locale,
                    HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView(PageView.INV_ADD_CONTENT);
        ContentDTO content = contentDataService.findByLocaleAndCode(code, locale);
        String parentCode = code;
        int matches = StringUtils.countMatches(code, ".");
        ContentCreatorCmd contentCreator = new ContentCreatorCmd();
        if (matches == 1 && ( code.contains(LABEL) ||  code.contains(PLACEHOLDER) || code.contains(TIP) )) {
            parentCode = code.substring(0,code.indexOf(".")+1);
            contentCreator.setLabelValue(loadLabel(parentCode, locale).getContent());
            contentCreator.setTipValue(loadTip(parentCode, locale).getContent());
            contentCreator.setPlaceholderValue(loadPlaceholder(parentCode, locale).getContent());
        } else {
            contentCreator.setLabel(false);
            contentCreator.setTip(false);
            contentCreator.setPlaceholder(false);
        }
        contentCreator.setLocale(content.getLocale());
        contentCreator.setParentCode(content.getCode());
        contentCreator.setContent(content.getContent());
        modelAndView.addObject(PageCommand.CONTENT_CREATOR, contentCreator);
        return modelAndView;
    }
    
    private ContentDTO loadTip(String code, String locale){
        return contentDataService.findByLocaleAndCode(code + TIP, locale);
    }
    
    private ContentDTO loadLabel(String code, String locale){
        return contentDataService.findByLocaleAndCode(code + LABEL, locale);
    }
    
    private ContentDTO loadPlaceholder(String code, String locale){
        return contentDataService.findByLocaleAndCode(code + PLACEHOLDER, locale);
    }
   
    @RequestMapping(value="/createSQL", method = RequestMethod.POST)
    @ResponseBody
    public String createSQL(@ModelAttribute(PageCommand.CONTENT_CREATOR) ContentCreatorCmd cmd, BindingResult result, HttpServletRequest request) {
        StringBuffer sql = new StringBuffer();
        String locale = (StringUtils.isEmpty(cmd.getLocale()) ?  "en_GB" : cmd.getLocale());
        String mainContent = (StringUtils.isEmpty(cmd.getContent()) ?  "" : cmd.getContent());
        String mainCode = (StringUtils.isEmpty(cmd.getParentCode()) ?  "NoCode" : cmd.getParentCode());
        if (cmd.getLabel()) {
            createInsert(sql, mainCode + LABEL, locale, (StringUtils.isEmpty(cmd.getLabelValue()) ?  mainContent : cmd.getLabelValue()));
        }
        if (cmd.getTip()) {
            createInsert(sql, mainCode + TIP, locale, (StringUtils.isEmpty(cmd.getTipValue()) ?  mainContent : cmd.getTipValue()));
        }
        if (cmd.getPlaceholder()) {
            createInsert(sql, mainCode + PLACEHOLDER, locale, (StringUtils.isEmpty(cmd.getPlaceholderValue()) ?  mainContent : cmd.getPlaceholderValue()));
        }
        return new Gson().toJson(sql.toString());
    }
    
    private void createInsert(StringBuffer sql, String code, String locale, String content) {
        sql.append(String.format(INSERT_STATEMENT,  code, locale, content ));
    }
    
    
    
    
    
   

}

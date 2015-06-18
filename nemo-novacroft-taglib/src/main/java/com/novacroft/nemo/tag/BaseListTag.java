package com.novacroft.nemo.tag;

import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.novacroft.nemo.common.transfer.SelectListDTO;

/**
 * Base for list tags, eg select list, radio button list
 */
public abstract class BaseListTag extends TagSupport {
    private static final long serialVersionUID = 8351246537360415926L;
    protected String id;
    protected SelectListDTO selectList;
    protected String selectedValue = "";
    protected Boolean mandatory = Boolean.FALSE;
    protected Boolean showHint = Boolean.FALSE;
    protected Boolean useManagedContentForMeanings = Boolean.TRUE;
    protected String path;
    protected String pageName;
    protected String cssClass = "";

    protected String getContent(String code) {
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
        return ctx.getMessage(code, new Object[] {}, null);
    }

    protected String getLabelMarkUp(String forId, Boolean mandatory, String prompt) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<label for=");
        stringBuilder.append(forId);
        stringBuilder.append(">");
        stringBuilder.append(prompt);
        stringBuilder.append(((mandatory) ? "*" : ""));
        stringBuilder.append(" : ");
        stringBuilder.append("</label>");
        return stringBuilder.toString();
    }

    protected String getHintMarkUp(String hint) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<span class=\"hint hint-color\" >");
        stringBuilder.append(hint);
        stringBuilder.append("</span>");
        return stringBuilder.toString();
    }

    /**
     * Select list to render.
     */
    public void setSelectList(SelectListDTO selectList) {
        this.selectList = selectList;
    }

    /**
     * Option (value) in the select list that is currently selected
     */
    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    /**
     * cssClass in the select list that is currently selected
     */
    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    /**
     * HTML element ID
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Flag to indicate whether field is mandatory
     */
    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    /**
     * Flag to indicate whether field is mandatory
     */
    public void setShowHint(Boolean showHint) {
        this.showHint = showHint;
    }

    /**
     * Model object attribute to bind to
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Flag to control whether meanings (displayed values) are obtained from managed content.
     */
    public void setUseManagedContentForMeanings(Boolean useManagedContentForMeanings) {
        this.useManagedContentForMeanings = useManagedContentForMeanings;
    }
}

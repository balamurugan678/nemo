package com.novacroft.nemo.tfl.online.tag;

import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.common.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.web.util.HtmlUtils.htmlEscape;

/**
 * Tag implementation that renders a select and options tag suite.
 */
public class RadioButtonListTag extends BaseListTag {
    static final Logger logger = LoggerFactory.getLogger(RadioButtonListTag.class);
    protected Boolean showHeadingLabel = Boolean.TRUE;
    protected String fieldsetCssClass = StringUtil.EMPTY_STRING;

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        this.pageName = (String) pageContext.getRequest().getAttribute("pageName");

        String listName = this.selectList.getName().toLowerCase();
        try {
            if (this.showHeadingLabel) {
                out.println(getLabelMarkUp(this.id, this.mandatory, getContent(pageName + "." + id + ".label")));
            }

            out.println("<fieldset id=" + htmlEscape(this.id) + " class=\"radiobutton-list " + this.fieldsetCssClass + "\">");
            out.println("<ul>");
            for (SelectListOptionDTO option : this.selectList.getOptions()) {

                if (isBlank(this.selectedValue)) {
                    this.selectedValue = option.getValue();
                }

                out.print("<li>");
                out.print("<label for=\"");
                out.print(htmlEscape(option.getValue()));
                out.print("\">");
                if (this.useManagedContentForMeanings) {
                    out.print(getContent(pageName + "." + listName + "." + option.getValue().toLowerCase() + ".option"));
                } else {
                    out.print(htmlEscape(option.getMeaning()));
                }
                out.print("</label>");
                out.print("<input type=\"radio\" ");
                out.print("id=\"");
                out.print(htmlEscape(option.getValue()));
                out.print("\" ");
                out.print("name=\"");
                out.print(this.path);
                out.print("\" ");
                out.print("value=\"");
                out.print(htmlEscape(option.getValue()));
                out.print("\" ");
                out.print((this.selectedValue.equals(option.getValue()) ? "checked=\"checked\"" : ""));
                out.print("/>");
                out.println("</li>");
            }
            out.println("</ul>");
            out.println("</fieldset>");

            if (showHint) {
                out.println(getHintMarkUp(getContent(pageName + "." + id + ".hint")));
            }

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new JspException(e.getMessage(), e);
        }
        return SKIP_BODY;
    }

    public void setShowHeadingLabel(Boolean showHeadingLabel) {
        this.showHeadingLabel = showHeadingLabel;
    }

    public void setFieldsetCssClass(String fieldsetCssClass) {
        this.fieldsetCssClass = fieldsetCssClass;
    }

}

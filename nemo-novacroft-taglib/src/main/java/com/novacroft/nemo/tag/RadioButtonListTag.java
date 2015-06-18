package com.novacroft.nemo.tag;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novacroft.nemo.common.transfer.SelectListOptionDTO;

/**
 * Tag implementation that renders a select and options tag suite.
 */
public class RadioButtonListTag extends BaseListTag {

    private static final long serialVersionUID = 4787692806984803151L;
    static final Logger logger = LoggerFactory.getLogger(RadioButtonListTag.class);

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        this.pageName = (String) pageContext.getRequest().getAttribute("pageName");

        String listName = this.selectList.getName().toLowerCase();
        try {
            out.println(getLabelMarkUp(this.id, this.mandatory, getContent(pageName + "." + id + ".label")));

            out.println("<fieldset class=\"radiobutton-list\">");
            out.println("<ul>");
            for (SelectListOptionDTO option : this.selectList.getOptions()) {

                if (isBlank(this.selectedValue)) {
                    this.selectedValue = option.getValue();
                }

                out.print("<li>");
                out.print("<label for=\"");
                out.print(option.getValue());
                out.print("\">");
                if (this.useManagedContentForMeanings) {
                    out.print(getContent(pageName + "." + listName + "." + option.getValue().toLowerCase() + ".option"));
                } else {
                    out.print(option.getMeaning());
                }
                out.print("</label>");
                out.print("<input type=\"radio\" ");
                out.print("id=\"");
                out.print(option.getValue());
                out.print("\" ");
                out.print("name=\"");
                out.print(this.path);
                out.print("\" ");
                out.print("value=\"");
                out.print(option.getValue());
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

}

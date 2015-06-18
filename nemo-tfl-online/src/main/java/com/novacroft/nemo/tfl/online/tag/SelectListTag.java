package com.novacroft.nemo.tfl.online.tag;

import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
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
public class SelectListTag extends BaseListTag {

    private static final long serialVersionUID = -8637135483760739342L;
    static final Logger logger = LoggerFactory.getLogger(SelectListTag.class);
    protected Boolean showPlaceholder = Boolean.TRUE;
    protected Integer size = 1;
    protected Boolean showLabel = Boolean.TRUE;

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        this.pageName = (String) pageContext.getRequest().getAttribute("pageName");

        String listName = this.selectList.getName().toLowerCase();
        try {

            if (showLabel) {
                out.println(getLabelMarkUp(this.id, this.mandatory, getContent(this.pageName + "." + id + ".label")));
            }

            out.println("<div class=\"form-control \">");
            out.println("<div class=\"selector \">");
            out.print("<select");
            out.print(" id=\"");
            out.print(this.id);
            out.print("\"");
            out.print(" name=\"");
            out.print(this.path);
            out.print("\"");
            out.print(" size=\"");
            out.print(this.size);
            out.print("\"");
            out.print(" pageAndListName=\"");
            out.print(pageName + "." + listName );
            out.print("\"");
            out.println(">");

            if (showPlaceholder) {
                out.print("<option value=\"\" ");
                out.print(((mandatory) ? "disabled " : ""));
                out.print((isBlank(this.selectedValue) ? "selected " : ""));
                out.print(">");
                out.print(getContent(pageName + "." + id + ".selectList.placeholder"));
                out.println("</option>");
            }

            for (SelectListOptionDTO option : this.selectList.getOptions()) {
                out.print("<option");
                out.print(" value=\"" + option.getValue() + "\"");
                out.print((this.selectedValue != null && this.selectedValue.equals(option.getValue()) ? " selected" : ""));
                out.print(option.getDisabled() ? " disabled" : "");
                out.print(">");
                if (this.useManagedContentForMeanings) {
                    out.print(getContent(pageName + "." + listName + "." + option.getValue().toLowerCase() + ".option"));
                } else {
                    out.print(htmlEscape(option.getMeaning()));
                }
                out.println("</option>");
            }

            out.println("</select>");
            out.println("</div>");
            out.println("</div>");

            if (showHint) {
                out.println(getHintMarkUp(getContent(pageName + "." + id + ".hint")));
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new JspException(e.getMessage(), e);
        }
        return SKIP_BODY;
    }

    /**
     * Flag to control display of 'please select...' option
     */
    public void setShowPlaceholder(Boolean showPlaceholder) {
        this.showPlaceholder = showPlaceholder;
    }

    /**
     * Flag to control display of label
     */
    public void setShowLabel(Boolean showLabel) {
        this.showLabel = showLabel;
    }

    /**
     * Number of options to display
     */
    public void setSize(Integer size) {
        this.size = size;
    }
}

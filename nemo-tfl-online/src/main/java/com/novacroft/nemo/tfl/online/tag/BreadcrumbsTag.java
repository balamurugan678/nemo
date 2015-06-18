package com.novacroft.nemo.tfl.online.tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Arrays;

/**
 * JSP Tag to render TfL online bread crumbs
 * <p/>
 * <p>
 * This tag takes an array of page names and creates bread crumbs for those page names.  The bread crumbs are
 * constructed using managed content.  The following content is expected:
 * </p>
 * <ul>
 * <li><code>home.breadcrumb.url</code>: The URL the home bread crumb should navigate to.</li>
 * <li><code>home.breadcrumb.tip</code>: The tool tip for the home bread crumb.</li>
 * <li><code>home.breadcrumb.link</code>: The displayed link text for the home bread crumb.</li>
 * <li><code>&lt;page-name&gt;.breadcrumb.url</code>: The URL the page bread crumb should navigate to.</li>
 * <li><code>&lt;page-name&gt;.breadcrumb.tip</code>: The tool tip for the page bread crumb.</li>
 * <li><code>&lt;page-name&gt;.breadcrumb.link</code>: The displayed link text for the page bread crumb.</li>
 * </ul>
 */
public class BreadcrumbsTag extends TagSupport {
    private static final long serialVersionUID = 3296366706170548549L;
    static final Logger logger = LoggerFactory.getLogger(BreadcrumbsTag.class);
    protected String[] pageNames;

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        if (this.pageNames == null || 0 == this.pageNames.length) {
            return SKIP_BODY;
        }

        try {
            out.println("<div class='r'>");
            out.println("<div class='breadcrumb-container'>");
            out.println("<ul class='breadcrumbs clearfix'>");

            out.println(String.format(
                    "<li class='home'><a href='%s' title='%s'><span class='hide-text'>%s</span></a></li>",
                    getContent("home.breadcrumb.url"), getContent("home.breadcrumb.tip"), getContent("home.breadcrumb.link")));

            for (int i = 0;
                 i < this.pageNames.length;
                 i++) {

                String pageName = this.pageNames[i];

                if (i < this.pageNames.length - 1) {
                    out.println(
                            String.format("<li><a href='%s' title='%s'>%s</a></li>", getContent(pageName + ".breadcrumb.url"),
                                    getContent(pageName + ".breadcrumb.tip"), getContent(pageName + ".breadcrumb.link")));
                } else {
                    out.println(String.format("<li><span class='last-breadcrumb'>%s</span></li>",
                            getContent(pageName + ".breadcrumb.link")));
                }
            }

            out.println("</ul>");
            out.println("</div>");
            out.println("</div>");

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new JspException(e.getMessage(), e);
        }
        return SKIP_BODY;
    }

    public void setPageNames(String[] newPageNames) {
        if (newPageNames == null) {
            this.pageNames = new String[0];
        } else {
            this.pageNames = Arrays.copyOf(newPageNames, newPageNames.length);
        }
    }

    private String getContent(String code) {
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
        return ctx.getMessage(code, new Object[]{}, null);
    }
}

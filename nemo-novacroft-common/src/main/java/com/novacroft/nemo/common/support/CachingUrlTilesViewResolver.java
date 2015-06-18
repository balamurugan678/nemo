package com.novacroft.nemo.common.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesView;

import java.util.Locale;

import static com.novacroft.nemo.common.constant.CommonPageAttribute.PAGE_NAME;

/**
 * Tiles View Resolver
 *
 * <p>Resolves view names to Tiles views by dropping the trailing 'View' from the view name.</p>
 *
 * <p>For example, a view name of <code>DashboardView</code> will be resolved to <code>Dashboard</code>.</p>
 *
 * <p>This resolver also adds the page name as an attribute to the view.</p>
 */
public class CachingUrlTilesViewResolver extends AbstractCachingViewResolver implements ViewResolver {
    private static final Logger logger = LoggerFactory.getLogger(CachingUrlTilesViewResolver.class);
    private static final String VIEW_NAME_SUFFIX = "View";
    private static final int NOT_FOUND_INDEX = -1;

    @Override
    protected View loadView(String viewName, Locale locale) throws Exception {
        if (isNotViewName(viewName)) {
            return null;
        }
        TilesView view = (TilesView) BeanUtils.instantiateClass(TilesView.class);
        String pageName = derivePageName(viewName);
        view.setUrl(pageName);
        addPageNameAttribute(view, pageName);
        return initializeSpringBean(view, viewName);
    }

    protected boolean isNotViewName(String viewName) {
        return NOT_FOUND_INDEX == viewName.lastIndexOf(VIEW_NAME_SUFFIX);
    }

    /*
     * The page name is derived by dropping the trailing 'View' from the view name.
     */
    protected String derivePageName(String viewName) {
        return viewName.substring(0, viewName.lastIndexOf(VIEW_NAME_SUFFIX));
    }

    protected void addPageNameAttribute(TilesView view, String pageName) {
        view.addStaticAttribute(PAGE_NAME, pageName);
    }

    protected View initializeSpringBean(View view, String viewName) {
        return (View) getApplicationContext().getAutowireCapableBeanFactory().initializeBean(view, viewName);
    }
}

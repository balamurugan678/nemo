package com.novacroft.nemo.mock_cubic.controller;

import javax.servlet.http.HttpServletResponse;

import static com.novacroft.nemo.mock_cubic.constant.Constant.CSV_MIME_TYPE;

/**
 * Mock CUBIC base controller
 */
public abstract class MockCubicBaseController {

    public void setAttachedFileNameOnResponseHeader(HttpServletResponse response, String fileName) {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
    }

    public void setContentTypeToCsvOnResponseHeader(HttpServletResponse response) {
        response.setContentType(CSV_MIME_TYPE);
    }
}

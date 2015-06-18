package com.novacroft.nemo.tfl.innovator.controller;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.constant.MimeContentType;
import com.novacroft.nemo.tfl.common.application_service.financial_services_centre.ExportFileNameService;
import com.novacroft.nemo.tfl.common.application_service.financial_services_centre.ExportFileService;
import com.novacroft.nemo.tfl.common.constant.*;
import com.novacroft.nemo.tfl.common.controller.BaseController;
import com.novacroft.nemo.tfl.innovator.command.ExportFileCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Export file controller
 */
@Controller
@RequestMapping(value = PageUrl.INV_EXPORT_FILE)
public class ExportFileController extends BaseController {

    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected ExportFileNameService exportFileNameService;
    @Autowired
    protected ExportFileService exportFileService;

    @ModelAttribute
    public void populateSelectList(Model model) {
        model.addAttribute(PageAttribute.EXPORT_FILE_TYPES,
                this.selectListService.getSelectList(PageSelectList.EXPORT_FILE_TYPE));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showPage() {
        return new ModelAndView(PageView.INV_EXPORT_FILE, PageCommand.EXPORT_FILE, new ExportFileCmd());
    }

    @RequestMapping(method = RequestMethod.POST)
    public void export(@ModelAttribute(PageCommand.EXPORT_FILE) ExportFileCmd cmd, HttpServletResponse response,
                       OutputStream outputStream) {
        assert (isNotBlank(cmd.getExportFileType()));
        String exportFileName = this.exportFileNameService.getExportFileName(cmd.getExportFileType());
        byte[] exportFile = this.exportFileService.exportFile(cmd.getExportFileType(), exportFileName);
        streamFile(response, outputStream, exportFileName, MimeContentType.CSV.code(), exportFile);
    }
}

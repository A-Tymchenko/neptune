package com.ra.advertisement.controller.post;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.advertisement.controller.Controller;
import com.ra.advertisement.controller.PathsEnum;
import com.ra.advertisement.service.ProviderAdvertisementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("saveProvider")
public class ProviderSaveController implements Controller {
    private final transient ProviderAdvertisementServiceImpl providerService;

    @Autowired
    public ProviderSaveController(final ProviderAdvertisementServiceImpl providerService) {
        this.providerService = providerService;
    }

    /**
     * This Controller puts into request the result of saving of Provider and return path to providerform.jsp.
     * @param request  HttpServlet request
     * @param response HttpServlet response
     * @return path to providerform.jsp
     */
    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        final List<String> answer = providerService.saveEntityService(request);
        request.setAttribute("result", answer);
        return PathsEnum.PROVIDER_FORM.getPath();
    }
}

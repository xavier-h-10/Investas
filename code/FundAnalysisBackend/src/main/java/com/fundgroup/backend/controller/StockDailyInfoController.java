package com.fundgroup.backend.controller;

import com.fundgroup.backend.service.StockDailyInfoService;
import com.fundgroup.backend.utils.messageUtils.Message;
import com.fundgroup.backend.utils.messageUtils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockDailyInfoController {
    @Autowired
    private StockDailyInfoService stockDailyInfoService;

    /**
     * TODO: modify to admin only
     * @return
     */
    @RequestMapping(value="/anyone/stock/daily/interpolation", method = RequestMethod.GET)
    public Message missingDateInterpolation()
    {
        stockDailyInfoService.missingDateInterpolation();
        return new Message(MessageUtil.SUCCESS);
    }
}

package org.example.controller;

import org.example.conf.ResponseResult;
import org.example.service.SgLinkService;
import org.example.service.impl.SgLinkServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
public class LinkController {
    //TOdo 这里到时候要调整一下
    @Autowired
    @Qualifier("sgLinkService")
    private SgLinkServiceImpl linkService;

    @GetMapping("/getAllLink")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }
}
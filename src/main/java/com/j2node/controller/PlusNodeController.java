package com.j2node.controller;

import com.j2node.pojo.bo.NodeScript;
import com.j2node.socket.SocketNodeService;
import com.j2node.utils.FileUtils;
import com.j2node.utils.ResourceLoadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PlusNodeController {


    @Autowired
    private SocketNodeService nodeService;

    @RequestMapping("test")
    public String testNode(NodeScript nodeScript) {
        String send = nodeService.send(nodeScript);
        return send;
    }

    @RequestMapping("start")
    public String startNode(NodeScript nodeScript) {
        String txtString = FileUtils.getTxtString(ResourceLoadUtils.loadResourceFilePath("/node/index.js"));
        nodeService.loadLocalNode(txtString);
        return "成功";
    }

    @RequestMapping("restart")
    public String restartNode(NodeScript nodeScript) {
        String txtString = FileUtils.getTxtString(ResourceLoadUtils.loadResourceFilePath("/node/index.js"));
        nodeService.restartLocalNode(txtString);
        return "重启成功";
    }

}

package pl.inz.ctscan.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.inz.ctscan.core.service.TestService;
import pl.inz.ctscan.core.service.queue.QueueService;
import pl.inz.ctscan.model.TestMessage;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    //private final QueueService queueService;

    @Autowired
    public TestController(TestService testService) {
            //, QueueService queueService) {
        this.testService = testService;
        //this.queueService = queueService;
    }

    @GetMapping("/{pathParam}")
    public TestMessage getTestMessage(@PathVariable String pathParam, @RequestParam String reqParam) {
        TestMessage t = new TestMessage();
        t.setTopSecret("<unforgettable text #" + pathParam + "/" + reqParam +">");
        return t;
    }

    @PostMapping("/add")
    public TestMessage addTestMessage(@RequestBody String body) {
        return testService.insertTestMessage(body);
    }

    @PostMapping("/list")
    public List<TestMessage> listTestMessage() {
        return testService.listTestMessage();
    }

    int start = 0;
/*
    @GetMapping("/executor")
    public boolean executeAim() throws InterruptedException {
        Long measurementId = 1L;
        String path = "/home/neo/_argo/tomografia/test/aim/test_76mb_full.aim" + start++;

        queueService.processAimFrames(measurementId, path);

        System.out.println("Poszedl thread");

        return true;
    }
    */
}

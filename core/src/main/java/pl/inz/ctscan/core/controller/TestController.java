package pl.inz.ctscan.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.inz.ctscan.core.service.TestService;
import pl.inz.ctscan.model.TestMessage;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

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
}

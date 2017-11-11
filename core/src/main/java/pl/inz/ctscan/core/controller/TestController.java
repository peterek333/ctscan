package pl.inz.ctscan.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.inz.ctscan.model.TestMessage;

@Controller
public class TestController {

    @GetMapping(path = "/test/{pathParam}", produces = "application/json")
    public @ResponseBody
    TestMessage getTest(@PathVariable String pathParam, @RequestParam String reqParam) {
        TestMessage t = new TestMessage();
        t.setTopSecret("<unforgettable text #" + pathParam + "/" + reqParam +">");
        return t;
    }
}

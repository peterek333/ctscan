package pl.inz.ctscan.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.inz.ctscan.db.TestRepository;
import pl.inz.ctscan.model.TestMessage;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public List<TestMessage> listTestMessage() {
        return testRepository.findAll();
    }

    public TestMessage createTestMessage(String topSecret) {
        TestMessage testMessage = new TestMessage();
        testMessage.setTopSecret(topSecret);

        return testMessage;
    }

    public TestMessage insertTestMessage(String topSecret) {
        TestMessage testMessage = createTestMessage(topSecret);

        return insertTestMessage(testMessage);
    }

    public TestMessage insertTestMessage(TestMessage testMessage) {
        return testRepository.insert(testMessage);
    }
}

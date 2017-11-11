package pl.inz.ctscan.db;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.inz.ctscan.model.TestMessage;

import java.util.List;

@Repository
public interface TestRepository extends MongoRepository<TestMessage, String> {

    List<TestMessage> findAllByTopSecret(String topSecret);
}

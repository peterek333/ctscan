package pl.inz.ctscan.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.inz.ctscan.model.TestMessage;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<TestMessage, Long> {

    List<TestMessage> findAllByTopSecret(String topSecret);
}

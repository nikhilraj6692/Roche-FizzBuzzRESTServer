package com.roche.test.repository;

import com.roche.dao.AuditRepository;
import com.roche.entity.AuditEntity;
import com.roche.model.Identifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TestAuditRepository {

    @Autowired
    AuditRepository auditRepository;

    @Test
    public void testSaveAndGetEntity() {
        List<AuditEntity> list = new ArrayList(){{
            add(new AuditEntity(UUID.randomUUID().toString(), new Date(), "body1", Identifier.GENERATE_FIZZ_BUZZ_ALGO.name()));
            add(new AuditEntity(UUID.randomUUID().toString(), new Date(), "body2", Identifier.NONE.name()));
        }};

        auditRepository.saveAll(list);

        assertEquals(auditRepository.findByIdentifier(Identifier.GENERATE_FIZZ_BUZZ_ALGO.name()).size(), 1);
    }

    @AfterEach
    public void destroy() {
        auditRepository.deleteAll();
    }

}
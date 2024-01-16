package com.roche.dao;

import com.roche.entity.AuditEntity;
import com.roche.model.Identifier;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends CrudRepository<AuditEntity, String>
{
    List<AuditEntity> findByIdentifier(String identifier);
}

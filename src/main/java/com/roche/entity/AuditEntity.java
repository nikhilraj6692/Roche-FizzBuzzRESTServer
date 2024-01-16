package com.roche.entity;

import com.roche.model.Identifier;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditEntity
{
    @Id
    private String uuid;
    private Date requestTimeStamp;
    private String requestBody;
    private String identifier;

    //more info can be added like methodType, url, response etc...not implementing for simplicity
}

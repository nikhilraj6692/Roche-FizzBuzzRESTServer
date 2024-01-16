package com.roche.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roche.annotation.Audit;
import com.roche.dao.AuditRepository;
import com.roche.entity.AuditEntity;
import com.roche.model.Identifier;
import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Aspect
@Component
public class AuditAspect {

    private final AuditRepository auditRepository;

    private final ObjectMapper objectMapper;

    public AuditAspect(AuditRepository auditRepository, ObjectMapper objectMapper) {
        this.auditRepository = auditRepository;
        this.objectMapper = objectMapper;
    }

    @Around("@annotation(com.roche.annotation.Audit)")
    public Object audit(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Annotation[][] annotationMatrix = methodSignature.getMethod().getParameterAnnotations();
        Object body = getRequestBody(joinPoint, annotationMatrix);

        String uuid = UUID.randomUUID().toString();
        Date currentTimeStamp = new Date();
        Identifier identifier = methodSignature.getMethod().getAnnotation(Audit.class).identifier();
        String payload = null;
        if(Objects.nonNull(body)) {
            payload = objectMapper.writeValueAsString(body);
        }

        AuditEntity entity = AuditEntity.builder()
            .uuid(uuid)
            .requestTimeStamp(currentTimeStamp)
            .identifier(identifier.name())
            .requestBody(payload)
            .build();

        auditRepository.save(entity);

        return joinPoint.proceed();
    }

    private static Object getRequestBody(ProceedingJoinPoint joinPoint, Annotation[][] annotationMatrix)
    {
        for (int i=0;i< annotationMatrix.length;i++) {
            for (Annotation annotation : annotationMatrix[i]) {
                if (!(annotation instanceof RequestBody))
                    continue;
                return joinPoint.getArgs()[i];
            }
        }
        return null;
    }
}
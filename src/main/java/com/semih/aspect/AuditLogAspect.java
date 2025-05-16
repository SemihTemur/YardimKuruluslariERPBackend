package com.semih.aspect;

import com.semih.model.Auditable;
import com.semih.model.Log;
import com.semih.model.User;
import com.semih.repository.LogRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditLogAspect {

    private final LogRepository logRepository;

    public AuditLogAspect(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @AfterReturning(value = "@annotation(auditable)", returning = "result")
    public void log(JoinPoint joinPoint, Auditable auditable, Object result) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = null;

        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication.getPrincipal() instanceof User user) {
                username = user.getDisplayUsername();
            } else {
                throw new RuntimeException("Lütfen giriş yapınız.");
            }
        }

        if (username == null) {
            throw new RuntimeException("Kullanıcı bulunamadı");
        }

        Log auditLog = new Log();
        auditLog.setActionType(auditable.actionType());
        auditLog.setTargetEntity(auditable.targetEntity());
        auditLog.setPerformedByUser(username);

        logRepository.save(auditLog);
    }
}

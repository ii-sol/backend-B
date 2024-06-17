package sinhan.server1.domain.notification.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sinhan.server1.domain.notification.service.SSEService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class NotificationController {

    private final SSEService sseService;

    
}

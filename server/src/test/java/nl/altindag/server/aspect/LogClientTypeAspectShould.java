package nl.altindag.server.aspect;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import nl.altindag.log.LogCaptor;

public class LogClientTypeAspectShould {

    private LogClientTypeAspect logClientTypeAspect = new LogClientTypeAspect();

    @Test
    public void logClientTypeIfPresent() {
        LogCaptor logCaptor = LogCaptor.forClass(LogClientTypeAspect.class);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("client-type", "okhttp");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        logClientTypeAspect.logClientTypeIfPresent();

        List<String> logs = logCaptor.getLogs();
        assertThat(logs).containsExactly("Received the request from the following client: okhttp");
    }

    @Test
    public void notLogClientTypeIfAbsent() {
        LogCaptor logCaptor = LogCaptor.forClass(LogClientTypeAspect.class);

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        logClientTypeAspect.logClientTypeIfPresent();

        List<String> logs = logCaptor.getLogs();
        assertThat(logs).isEmpty();
    }

}

package kalchenko.external;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import kalchenko.SecurityConfiguration;
import kalchenko.exception.TaskNotFoundException;
import kalchenko.security.Users;
import kalchenko.taskDTOLayer.TaskDTO;
import org.apache.http.impl.conn.Wire;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskServiceExternalTest {

    @Autowired
    TaskServiceExternal taskServiceExternal;

    static final String USER = "user";
    static final String PASSWORD = "1234";

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void startWireMock(){

        wireMockServer = new WireMockServer(wireMockConfig().port(54322));
        wireMockServer.start();

    }

    @AfterAll
    static void stopWireMock(){

        wireMockServer.stop();

    }
    @Test
    public void testWireMockSetUp(){

        assertEquals("http://localhost:54322", wireMockServer.baseUrl());
        assertTrue(wireMockServer.isRunning());

    }

    @Test
    public void runServerTest(){

        String description = "External Task with ";

        wireMockServer.stubFor(get(WireMock.urlEqualTo("/taskRest/")).willReturn(okJson("[" +
                "{" +
                "\"id\": 1," +
                "\"name\": \"External Task\"," +
                "\"completed\": true" +
                "}," +
                "{" +
                "\"id\": 2," +
                "\"name\": \"" + description + "\"," +
                "\"completed\": false" +
                "}" +
                "]")));


         List<TaskDTO> tasksDTO = taskServiceExternal.findAllByUserId(getUser());

        assertEquals(2, tasksDTO.size());


    }

    private static  Users getUser(){

        Users user = new Users();
        user.setName("user");
        user.setPassword("1234");
        user.setRole(SecurityConfiguration.USER_ROLE);

        return user;

    }



}

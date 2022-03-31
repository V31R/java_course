package kalchenko.external;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import kalchenko.SecurityConfiguration;
import kalchenko.exception.TaskNotFoundException;
import kalchenko.security.Users;
import kalchenko.taskDTOLayer.TaskDTO;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskServiceExternalTest {

    @Autowired
    TaskServiceExternal taskServiceExternal;

    private static final String USER = "user";
    private static final String PASSWORD = "1234";

    private static TestTask[] tasksArray;

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
    public void testFindAllByUserId(){

        setTasksArray();

        StringBuilder tasksJSON = new StringBuilder();
        tasksJSON.append("[");
        for( int i = 0; i < tasksArray.length;i++){

            if(i > 0){

                tasksJSON.append(",");

            }
            tasksJSON.append(makeJSONString(tasksArray[i]));

        }
        tasksJSON.append("]");

        wireMockServer.stubFor(get(WireMock.urlEqualTo("/taskRest/"))
                .withBasicAuth(USER, PASSWORD)
                .willReturn(okJson(tasksJSON.toString())));


        List<TaskDTO> tasksDTO = taskServiceExternal.findAllByUserId(getUser());

        assertEquals(tasksArray.length, tasksDTO.size());
        for( int i = 0; i < tasksArray.length;i++){

            assertEquals(tasksArray[i].id, tasksDTO.get(i).getId());
            assertEquals(tasksArray[i].name, tasksDTO.get(i).getDescription());
            assertEquals(tasksArray[i].completed, tasksDTO.get(i).isDone());

        }

    }

    @Test
    public void testFindByUserId_IfFounded(){

        int taskIndex = 0;

        setTasksArray();

        wireMockServer.stubFor(get(WireMock.urlEqualTo("/taskRest/"))
                .withBasicAuth(USER, PASSWORD)
                .willReturn(okJson(makeJSONString(tasksArray[taskIndex]))));

        TaskDTO taskToFind = new TaskDTO();
        taskToFind.setId(String.valueOf(taskIndex + 1));
        TaskDTO taskDTO = taskServiceExternal.findByUserId(taskToFind,getUser());

        assertEquals(tasksArray[taskIndex].id, taskDTO.getId());
        assertEquals(tasksArray[taskIndex].name, taskDTO.getDescription());
        assertEquals(tasksArray[taskIndex].completed, taskDTO.isDone());


    }

    @Test
    public void testFindByUserId_IfNotFounded(){

        wireMockServer.stubFor(get(WireMock.urlEqualTo("/taskRest/"))
                .withBasicAuth(USER, PASSWORD)
                .willReturn(okJson("")));

        TaskDTO taskToFind = new TaskDTO();
        taskToFind.setId(String.valueOf(1));
        try {

            TaskDTO taskDTO = taskServiceExternal.findByUserId(taskToFind, getUser());
            fail("Expected" + TaskNotFoundException.class);

        }
        catch (TaskNotFoundException taskNotFoundException){

            assertNotNull(taskNotFoundException);

        }

    }


    private static  Users getUser(){

        Users user = new Users();
        user.setName(USER);
        user.setPassword(PASSWORD);
        user.setRole(SecurityConfiguration.USER_ROLE);

        return user;

    }

    static class TestTask{

        public String id;
        public String name;
        public boolean completed;

    }

    static void setTasksArray(){

        tasksArray = new TestTask[2];
        for( int i = 0; i < tasksArray.length;i++){
            tasksArray[i]=new TestTask();
        }
        tasksArray[0].id="1";
        tasksArray[0].name= "description_first";
        tasksArray[0].completed = true;

        tasksArray[1].id="2";
        tasksArray[1].name= "description_second";
        tasksArray[1].completed = false;

    }

    static String makeJSONString(TestTask testTask){

        StringBuilder builder = new StringBuilder();

        builder.append("{ \"id\": ")
                .append(testTask.id)
                .append(", \"name\": \"")
                .append(testTask.name)
                .append("\", \"completed\" :")
                .append(testTask.completed)
                .append("}");

        return builder.toString();

    }


}

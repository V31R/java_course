package kalchenko.external;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import kalchenko.SecurityConfiguration;
import kalchenko.exception.TaskNotFoundException;
import kalchenko.security.Users;
import kalchenko.taskDTOLayer.TaskDTO;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${to_do_list.external.base_url}")
    private String BASE_URL_PART;

    @Value("${to_do_list.external.prefix}")
    private String prefix;

    private static TestTask[] tasksArray;

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void startWireMock(){

        wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());
        wireMockServer.start();

    }
    @BeforeEach
    void setPortService(){

        taskServiceExternal.setPort(wireMockServer.port());

    }

    @AfterAll
    static void stopWireMock(){

        wireMockServer.stop();

    }

    @Test
    public void testWireMockSetUp(){

        assertEquals("http://localhost:" + taskServiceExternal.getPort(), wireMockServer.baseUrl());
        assertTrue(wireMockServer.isRunning());

    }

    @Test
    public void testFindAllByUserId_IfFounded(){

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

        wireMockServer.stubFor(get(WireMock.urlEqualTo(BASE_URL_PART))
                .withBasicAuth(USER, PASSWORD)
                .willReturn(okJson(tasksJSON.toString())));


        List<TaskDTO> tasksDTO = taskServiceExternal.findAllByUserId(getUser());

        assertEquals(tasksArray.length, tasksDTO.size());
        for( int i = 0; i < tasksArray.length;i++){

            assertEquals(prefix+tasksArray[i].id, tasksDTO.get(i).getId());
            assertEquals(tasksArray[i].name, tasksDTO.get(i).getDescription());
            assertEquals(tasksArray[i].completed, tasksDTO.get(i).isDone());

        }

    }



    @Test
    public void testFindByUserId_IfFounded(){

        int taskIndex = 0;

        setTasksArray();

        wireMockServer.stubFor(get(WireMock.urlEqualTo(BASE_URL_PART+"1"))
                .withBasicAuth(USER, PASSWORD)
                .willReturn(okJson(makeJSONString(tasksArray[taskIndex]))));

        TaskDTO taskToFind = new TaskDTO();
        taskToFind.setId(prefix + String.valueOf(taskIndex + 1));
        TaskDTO taskDTO = taskServiceExternal.findByUserId(taskToFind,getUser());

        assertEquals(prefix + tasksArray[taskIndex].id, taskDTO.getId());
        assertEquals(tasksArray[taskIndex].name, taskDTO.getDescription());
        assertEquals(tasksArray[taskIndex].completed, taskDTO.isDone());


    }

    @Test
    public void testFindByUserId_IfNotFounded(){

        wireMockServer.stubFor(get(WireMock.urlEqualTo(BASE_URL_PART+"1"))
                .withBasicAuth(USER, PASSWORD)
                .willReturn(okJson("")));

        TaskDTO taskToFind = new TaskDTO();
        taskToFind.setId(prefix+ String.valueOf(1));
        try {

            TaskDTO taskDTO = taskServiceExternal.findByUserId(taskToFind, getUser());
            fail("Expected" + TaskNotFoundException.class);

        }
        catch (TaskNotFoundException taskNotFoundException){

            assertNotNull(taskNotFoundException);

        }

    }

    @Test
    public void testSave(){

        int taskIndex = 1;

        setTasksArray();

        wireMockServer.stubFor(post(WireMock.urlEqualTo(BASE_URL_PART
                + "add?name="+tasksArray[taskIndex].name))
                .withBasicAuth(USER, PASSWORD)
                .willReturn(okJson(makeJSONString(tasksArray[taskIndex]))));

        TaskDTO taskToSave = new TaskDTO();
        taskToSave.setDescription(tasksArray[taskIndex].name);
        TaskDTO taskDTO = taskServiceExternal.save(taskToSave, getUser());

        assertEquals(prefix + tasksArray[taskIndex].id, taskDTO.getId());
        assertEquals(tasksArray[taskIndex].name, taskDTO.getDescription());
        assertEquals(tasksArray[taskIndex].completed, taskDTO.isDone());


    }

    @Test
    public void testDelete(){

        String taskId = "1";

        wireMockServer.stubFor(delete(BASE_URL_PART
                + "delete/"+taskId)
                .withBasicAuth(USER, PASSWORD)
                .willReturn(ok("")));

        TaskDTO taskToDelete = new TaskDTO();
        taskToDelete.setId(prefix + taskId);
        taskServiceExternal.deleteById(taskToDelete, getUser());

        wireMockServer.verify(deleteRequestedFor(WireMock.urlEqualTo(BASE_URL_PART
                + "delete/"+taskId)));



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

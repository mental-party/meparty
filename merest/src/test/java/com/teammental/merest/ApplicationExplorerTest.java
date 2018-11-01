package com.teammental.merest;

import static org.junit.Assert.assertEquals;

import com.teammental.merest.autoconfiguration.ApplicationExplorer;
import com.teammental.merest.testrestapi.TestApplicationEnableRestApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = {TestApplicationEnableRestApi.class})
public class ApplicationExplorerTest {

  @Autowired
  private ApplicationExplorer applicationExplorer;

  @Test
  public void shouldContainApplicationsInResources() {
    String testApp2Url = applicationExplorer.getApplication("testapp2");
    String testApp3Url = applicationExplorer.getApplication("testapp3");
    assertEquals("http://testapp2.com", testApp2Url);
    assertEquals("http://testapp3.com", testApp3Url);
  }

}
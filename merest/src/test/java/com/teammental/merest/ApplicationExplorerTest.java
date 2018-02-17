package com.teammental.merest;

import static org.junit.Assert.assertEquals;

import com.teammental.merest.testapp.TestApplication;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = {TestApplication.class})
public class ApplicationExplorerTest {

  private ApplicationExplorer applicationExplorer = ApplicationExplorer.getInstance();

  @Test
  @Ignore
  public void shouldContainApplicationsInResources() {
    String testApp2Url = applicationExplorer.getApplication("testapp2");
    String testApp3Url = applicationExplorer.getApplication("testapp3");
    assertEquals("http://testapp2.com", testApp2Url);
    assertEquals("http://testapp3.com", testApp3Url);
  }

}
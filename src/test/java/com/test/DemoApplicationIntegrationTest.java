package com.test;

import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {DemoApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoApplicationIntegrationTest {

    @LocalServerPort
    private int port;

    @Test
    public void post() {
        final HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        httpClientBuilder
                .setMaxConnPerRoute(20)
                .setMaxConnTotal(120);

        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClientBuilder.build());
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("naman");

        HttpEntity<Employee> request = new HttpEntity<>(employee);

        ResponseEntity<Employee> employeeResponseEntity = restTemplate.postForEntity("http://localhost:" + port + "/emp/create", request, Employee.class);
        assertThat(employeeResponseEntity.getBody().getName()).isEqualTo("naman");

        Employee found = restTemplate.getForObject("http://localhost:" + port + "/emp/1", Employee.class);
        assertThat(found.getName()).isEqualTo("naman");
        assertThat(found.getId()).isEqualTo(1);
    }

}

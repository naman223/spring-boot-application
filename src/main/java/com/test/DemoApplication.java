package com.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.metric.SpanMetricReporter;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.cloud.sleuth.zipkin.HttpZipkinSpanReporter;
import org.springframework.cloud.sleuth.zipkin.ZipkinProperties;
import org.springframework.cloud.sleuth.zipkin.ZipkinSpanReporter;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import zipkin.Span;

@SpringBootApplication
public class DemoApplication {

	Log log = LogFactory.getLog(DemoApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	/**
	 * to get all sampler data in zipkin.
	 * @return
	 */
	@Bean
	public AlwaysSampler defaultSampler() {
		return new AlwaysSampler();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public ZipkinSpanReporter makeZipkinSpanReporter() {
		return new ZipkinSpanReporter() {
			private HttpZipkinSpanReporter delegate;

			@Autowired
			ZipkinProperties zipkinProperties;

			@Autowired
			SpanMetricReporter spanMetricReporter;

			@Override
			public void report(Span span) {
				try {
					delegate = new HttpZipkinSpanReporter(restTemplate(), zipkinProperties.getBaseUrl(), zipkinProperties.getFlushInterval(),
							spanMetricReporter);
					delegate.report(span);
				} catch (ResourceAccessException exception) {
					log.warn("Make Sure Zipkin is up and running on Port 9411 !!!!");
				}			}

		};
	}

}

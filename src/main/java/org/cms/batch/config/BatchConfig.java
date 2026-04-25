package org.cms.batch.config;

import org.cms.batch.processor.CustomerItemProcessor;
import org.cms.batch.reader.CustomerExcelReader;
import org.cms.batch.writer.CustomerBatchWriter;
import org.cms.dto.CustomerExcelDto;
import org.cms.entity.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {


    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    private final CustomerItemProcessor processor;
    private final CustomerBatchWriter writer;

    public BatchConfig(CustomerItemProcessor processor, CustomerBatchWriter writer) {
        this.processor = processor;
        this.writer = writer;
    }

    @Bean
    @StepScope
    public CustomerExcelReader customerExcelReader(@Value("#{jobParameters['filePath']}") String filePath) {
        return new CustomerExcelReader(filePath);
    }

    @Bean
    public Step customerUploadStep(CustomerExcelReader reader) {
        return stepBuilderFactory.get("customerUploadStep")
                .<CustomerExcelDto, Customer>chunk(1000)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job customerUploadJob(Step customerUploadStep) {
        return jobBuilderFactory.get("customerUploadJob")
                .start(customerUploadStep)
                .build();
    }
}
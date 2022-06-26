package com.lightson.findpropapi.crawler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lightson.findpropapi.crawler.model.SourceRentPrice;
import com.lightson.findpropapi.crawler.model.TargetRentPrice;
import com.lightson.findpropapi.crawler.processor.RentPriceItemProcessor;
import com.lightson.findpropapi.crawler.reader.PostcodeAreaRentPriceReader;
import com.lightson.findpropapi.crawler.writer.CompositeRentPriceWriter;

@Configuration
@EnableBatchProcessing
public class CrawlerConfiguration {

        @Autowired
        public JobBuilderFactory jobs;

        @Autowired
        public StepBuilderFactory steps;

        @Bean
        public Job job(@Qualifier("rentPriceCrawlStep") Step rentPriceCrawlStep) {
                return jobs
                                .get("findPropCrawlJob")
                                .start(rentPriceCrawlStep)
                                .build();
        }

        @Bean
        protected Step rentPriceCrawlStep(PostcodeAreaRentPriceReader reader,
                        RentPriceItemProcessor processor,
                        CompositeRentPriceWriter writer) {
                return steps.get("rentPriceCrawlStep")
                                .<SourceRentPrice, TargetRentPrice>chunk(10)
                                .reader(reader)
                                .processor(processor)
                                .writer(writer)
                                .allowStartIfComplete(true)
                                .build();
        }
}

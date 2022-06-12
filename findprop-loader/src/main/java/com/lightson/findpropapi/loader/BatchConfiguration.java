package com.lightson.findpropapi.loader;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.lightson.findpropapi.loader.model.SourceLocalAuthority;
import com.lightson.findpropapi.loader.model.SourceLocalAuthorityRentPrice;
import com.lightson.findpropapi.loader.model.SourcePostcode;
import com.lightson.findpropapi.loader.model.SourcePostcodeAreaRentPrice;
import com.lightson.findpropapi.loader.model.SourceRegion;
import com.lightson.findpropapi.loader.model.SourceRegionRentPrice;
import com.lightson.findpropapi.loader.model.SourceRelatedLocalAuthority;
import com.lightson.findpropapi.loader.model.TargetLocalAuthority;
import com.lightson.findpropapi.loader.model.TargetLocalAuthorityRentPrice;
import com.lightson.findpropapi.loader.model.TargetPostcode;
import com.lightson.findpropapi.loader.model.TargetPostcodeAreaRentPrice;
import com.lightson.findpropapi.loader.model.TargetRegion;
import com.lightson.findpropapi.loader.model.TargetRegionRentPrice;
import com.lightson.findpropapi.loader.model.TargetRelatedLocalAuthority;
import com.lightson.findpropapi.loader.processor.LocalAuthorityItemProcessor;
import com.lightson.findpropapi.loader.processor.LocalAuthorityRentPriceItemProcessor;
import com.lightson.findpropapi.loader.processor.PostcodeAreaRentPriceItemProcessor;
import com.lightson.findpropapi.loader.processor.PostcodeItemProcessor;
import com.lightson.findpropapi.loader.processor.RegionItemProcessor;
import com.lightson.findpropapi.loader.processor.RegionRentPriceItemProcessor;
import com.lightson.findpropapi.loader.processor.RelatedLocalAuthorityItemProcessor;
import com.lightson.findpropapi.loader.reader.LocalAuthorityReader;
import com.lightson.findpropapi.loader.reader.LocalAuthorityRentPriceReader;
import com.lightson.findpropapi.loader.reader.PostcodeAreaRentPriceReader;
import com.lightson.findpropapi.loader.reader.PostcodeReader;
import com.lightson.findpropapi.loader.reader.RegionReader;
import com.lightson.findpropapi.loader.reader.RegionRentPriceReader;
import com.lightson.findpropapi.loader.reader.RelatedLocalAuthorityReader;
import com.lightson.findpropapi.loader.writer.LocalAuthorityRentPriceWriter;
import com.lightson.findpropapi.loader.writer.LocalAuthorityWriter;
import com.lightson.findpropapi.loader.writer.PostcodeAreaRentPriceWriter;
import com.lightson.findpropapi.loader.writer.PostcodeWriter;
import com.lightson.findpropapi.loader.writer.RegionRentPriceWriter;
import com.lightson.findpropapi.loader.writer.RegionWriter;
import com.lightson.findpropapi.loader.writer.RelatedLocalAuthorityWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

        @Autowired
        public JobBuilderFactory jobs;

        @Autowired
        public StepBuilderFactory steps;

        @Bean
        public Job job(@Qualifier("regionImportStep") Step regionImportStep,
                        @Qualifier("localAuthorityImportStep") Step localAuthorityImportStep,
                        @Qualifier("relatedLocalAuthorityImportStep") Step relatedLocalAuthorityImportStep,
                        @Qualifier("postcodeImportStep") Step postcodeImportStep,
                        @Qualifier("regionRentPriceImportStep") Step regionRentPriceImportStep,
                        @Qualifier("localAuthorityRentPriceImportStep") Step localAuthorityRentPriceImportStep,
                        @Qualifier("postcodeAreaRentPriceImportStep") Step postcodeAreaRentPriceImportStep) {
                return jobs
                                .get("findPropImportJob")
                                .start(regionImportStep)
                                .next(localAuthorityImportStep)
                                .next(relatedLocalAuthorityImportStep)
                                .next(postcodeImportStep)
                                .next(regionRentPriceImportStep)
                                .next(localAuthorityRentPriceImportStep)
                                .next(postcodeAreaRentPriceImportStep)
                                .build();
        }

        @Bean
        protected Step regionImportStep(RegionReader reader, RegionItemProcessor processor,
                        RegionWriter writer) {
                return steps.get("regionImportStep")
                                .<SourceRegion, TargetRegion>chunk(10)
                                .reader(reader)
                                .processor(processor)
                                .writer(writer)
                                .allowStartIfComplete(true)
                                .build();
        }

        @Bean
        protected Step localAuthorityImportStep(LocalAuthorityReader reader, LocalAuthorityItemProcessor processor,
                        LocalAuthorityWriter writer) {
                return steps.get("localAuthorityImportStep")
                                .<SourceLocalAuthority, TargetLocalAuthority>chunk(10)
                                .reader(reader)
                                .processor(processor)
                                .writer(writer)
                                .allowStartIfComplete(true)
                                .build();
        }

        @Bean
        protected Step relatedLocalAuthorityImportStep(RelatedLocalAuthorityReader reader,
                        RelatedLocalAuthorityItemProcessor processor,
                        RelatedLocalAuthorityWriter writer) {
                return steps.get("relatedLocalAuthorityImportStep")
                                .<SourceRelatedLocalAuthority, TargetRelatedLocalAuthority>chunk(10)
                                .reader(reader)
                                .processor(processor)
                                .writer(writer)
                                .allowStartIfComplete(true)
                                .build();
        }

        @Bean
        protected Step postcodeImportStep(PostcodeReader reader, PostcodeItemProcessor processor,
                        PostcodeWriter writer) {
                return steps.get("postcodeImportStep")
                                .<SourcePostcode, TargetPostcode>chunk(10)
                                .reader(reader)
                                .processor(processor)
                                .writer(writer)
                                .allowStartIfComplete(true)
                                .build();
        }

        @Bean
        protected Step regionRentPriceImportStep(RegionRentPriceReader reader, RegionRentPriceItemProcessor processor,
                        RegionRentPriceWriter writer) {
                return steps.get("regionRentPriceImportStep")
                                .<SourceRegionRentPrice, TargetRegionRentPrice>chunk(10)
                                .reader(reader)
                                .processor(processor)
                                .writer(writer)
                                .allowStartIfComplete(true)
                                .build();
        }

        @Bean
        protected Step localAuthorityRentPriceImportStep(LocalAuthorityRentPriceReader reader,
                        LocalAuthorityRentPriceItemProcessor processor,
                        LocalAuthorityRentPriceWriter writer) {
                return steps.get("localAuthorityRentPriceImportStep")
                                .<SourceLocalAuthorityRentPrice, TargetLocalAuthorityRentPrice>chunk(10)
                                .reader(reader)
                                .processor(processor)
                                .writer(writer)
                                .allowStartIfComplete(true)
                                .build();
        }

        @Bean
        protected Step postcodeAreaRentPriceImportStep(PostcodeAreaRentPriceReader reader,
                        PostcodeAreaRentPriceItemProcessor processor,
                        PostcodeAreaRentPriceWriter writer) {
                return steps.get("postcodeAreaRentPriceImportStep")
                                .<SourcePostcodeAreaRentPrice, TargetPostcodeAreaRentPrice>chunk(10)
                                .reader(reader)
                                .processor(processor)
                                .writer(writer)
                                .allowStartIfComplete(true)
                                .build();
        }
}

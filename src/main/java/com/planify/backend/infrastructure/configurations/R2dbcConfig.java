package com.planify.backend.infrastructure.configurations;

import com.planify.backend.infrastructure.converters.GoalTypeReadConverter;
import com.planify.backend.infrastructure.converters.GoalTypeWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;

import java.util.List;

@Configuration
public class R2dbcConfig {

    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions(
            GoalTypeReadConverter readConverter,
            GoalTypeWriteConverter writeConverter
    ) {
        return new R2dbcCustomConversions(
                CustomConversions.StoreConversions.NONE,
                List.of(readConverter, writeConverter)
        );
    }
}

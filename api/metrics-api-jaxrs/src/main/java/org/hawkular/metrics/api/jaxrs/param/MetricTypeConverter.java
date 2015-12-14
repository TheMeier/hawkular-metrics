/*
 * Copyright 2014-2015 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hawkular.metrics.api.jaxrs.param;

import javax.ws.rs.ext.ParamConverter;

import org.hawkular.metrics.models.MetricType;


/**
 * A JAX-RS {@link ParamConverter} for {@link MetricType} parameters.
 *
 * @author Thomas Segismont
 */
public class MetricTypeConverter implements ParamConverter<MetricType<?>> {

    @Override
    public MetricType<?> fromString(String s) {
        return MetricType.fromTextCode(s);
    }

    @Override
    public String toString(MetricType<?> metricType) {
        return metricType.getText();
    }
}

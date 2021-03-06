/*
 * Copyright 2014-2016 Red Hat, Inc. and/or its affiliates
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

package org.hawkular.metrics.core.service.transformers;

import java.util.List;

import org.hawkular.metrics.model.Buckets;
import org.hawkular.metrics.model.DataPoint;
import org.hawkular.metrics.model.NumericBucketPoint;

import rx.Observable;
import rx.Observable.Transformer;

/**
 * @author Thomas Segismont
 */
public class NumericBucketPointTransformer
        implements Transformer<DataPoint<? extends Number>, List<NumericBucketPoint>> {

    private final Buckets buckets;
    private final List<Double> percentiles;

    public NumericBucketPointTransformer(Buckets buckets, List<Double> percentiles) {
        this.buckets = buckets;
        this.percentiles = percentiles;
    }

    @Override
    public Observable<List<NumericBucketPoint>> call(Observable<DataPoint<? extends Number>> dataPoints) {
        return dataPoints
                .groupBy(dataPoint -> buckets.getIndex(dataPoint.getTimestamp()))
                .flatMap(group -> group.collect(()
                                -> new NumericDataPointCollector(buckets, group.getKey(), percentiles),
                        NumericDataPointCollector::increment))
                .map(NumericDataPointCollector::toBucketPoint)
                .toMap(NumericBucketPoint::getStart)
                .map(pointMap -> NumericBucketPoint.toList(pointMap, buckets));
    }
}

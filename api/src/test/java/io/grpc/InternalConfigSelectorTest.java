/*
 * Copyright 2020 The gRPC Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.grpc;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Tests for {@link InternalConfigSelector}. */
@RunWith(JUnit4.class)
public class InternalConfigSelectorTest {
  @Test
  public void resultBuilder() {
    Object config = "fake_config";
    CallOptions callOptions = CallOptions.DEFAULT.withAuthority("fake authority");
    Runnable committedCallback = new Runnable() {
      @Override
      public void run() {}
    };
    InternalConfigSelector.Result.Builder builder = InternalConfigSelector.Result.newBuilder();

    InternalConfigSelector.Result result =
        builder.setConfig(config).setCallOptions(callOptions).build();
    assertThat(result.getConfig()).isEqualTo(config);
    assertThat(result.getCallOptions()).isEqualTo(callOptions);
    assertThat(result.getCommittedCallback()).isNull();

    result = builder
        .setConfig(config)
        .setCallOptions(callOptions)
        .setCommittedCallback(committedCallback)
        .build();
    assertThat(result.getConfig()).isEqualTo(config);
    assertThat(result.getCallOptions()).isEqualTo(callOptions);
    assertThat(result.getCommittedCallback()).isSameInstanceAs(committedCallback);
  }
}

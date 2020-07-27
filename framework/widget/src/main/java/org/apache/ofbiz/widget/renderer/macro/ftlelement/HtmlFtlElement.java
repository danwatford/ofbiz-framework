/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package org.apache.ofbiz.widget.renderer.macro.ftlelement;

import lombok.ToString;

import java.util.function.Consumer;

@ToString
public final class HtmlFtlElement implements FtlElement {
    private final String html;

    public HtmlFtlElement(final String html) {
        this.html = html;
    }

    public String getHtml() {
        return html;
    }

    public static HtmlFtlElementBuilder builder() {
        return new HtmlFtlElementBuilder();
    }

    public static HtmlFtlElement withStringBuilder(final Consumer<StringBuilder> callback) {
        final HtmlFtlElementBuilder builder = builder();
        callback.accept(builder.getStringBuilder());
        return builder.build();
    }

    public static final class HtmlFtlElementBuilder {
        private final StringBuilder stringBuilder = new StringBuilder();

        public StringBuilder getStringBuilder() {
            return stringBuilder;
        }

        public HtmlFtlElement build() {
            return new HtmlFtlElement(stringBuilder.toString());
        }
    }
}

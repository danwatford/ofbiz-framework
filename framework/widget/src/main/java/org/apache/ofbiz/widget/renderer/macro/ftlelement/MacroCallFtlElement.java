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
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.ofbiz.widget.renderer.macro.parameter.MacroCallParameterBooleanValue;
import org.apache.ofbiz.widget.renderer.macro.parameter.MacroCallParameterMapValue;
import org.apache.ofbiz.widget.renderer.macro.parameter.MacroCallParameterStringValue;
import org.apache.ofbiz.widget.renderer.macro.parameter.MacroCallParameterValue;

/**
 * Represents a call to a macro along with the call parameters.
 */
@ToString
public final class MacroCallFtlElement implements FtlElement {
    private final String name;
    private final Map<String, MacroCallParameterValue> parameters;

    private MacroCallFtlElement(final String name, final Map<String, MacroCallParameterValue> parameters) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Macro name cannot be blank.");
        }
        this.name = name;
        this.parameters = parameters != null ? new HashMap<>(parameters) : new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Stream<Map.Entry<String, MacroCallParameterValue>> getParameterStream() {
        return parameters.entrySet()
                .stream();
    }

    public MacroCallParameterValue getParameter(final String name) {
        return parameters.get(name);
    }

    public static FtlMacroCallBuilder builder(final String macroName) {
        return new FtlMacroCallBuilder(macroName);
    }

    public static final class FtlMacroCallBuilder {
        private final String name;
        private final Map<String, MacroCallParameterValue> parameters = new HashMap<>();

        public FtlMacroCallBuilder(final String macroName) {
            this.name = macroName;
        }

        public FtlMacroCallBuilder stringParameter(final String parameterName, final String parameterValue) {
            parameters.put(parameterName, new MacroCallParameterStringValue(parameterValue));
            return this;
        }

        public FtlMacroCallBuilder booleanParameter(final String parameterName, final boolean parameterValue) {
            parameters.put(parameterName, new MacroCallParameterBooleanValue(parameterValue));
            return this;
        }

        public FtlMacroCallBuilder mapParameter(final String parameterName, final Map<String, String> parameterValue) {
            parameters.put(parameterName, new MacroCallParameterMapValue(parameterValue));
            return this;
        }

        public MacroCallFtlElement build() {
            return new MacroCallFtlElement(name, parameters);
        }
    }

    public static FtlElement of(final String macroName, final Map<String, MacroCallParameterValue> parameters) {
        if (StringUtils.isBlank(macroName)) {
            throw new IllegalArgumentException("Macro name cannot be blank.");
        }

        return new MacroCallFtlElement(macroName, parameters);
    }
}

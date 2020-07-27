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
package org.apache.ofbiz.widget.renderer.macro;

import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.widget.renderer.macro.ftlelement.MacroCallFtlElement;
import org.apache.ofbiz.widget.renderer.macro.parameter.MacroCallParameterBooleanValue;
import org.apache.ofbiz.widget.renderer.macro.parameter.MacroCallParameterMapValue;
import org.apache.ofbiz.widget.renderer.macro.parameter.MacroCallParameterStringValue;
import org.apache.ofbiz.widget.renderer.macro.parameter.MacroCallParameterValue;

import java.util.Map;
import java.util.stream.Collectors;

public final class DefaultFtlMacroCallStringRenderer implements FtlMacroCallStringRenderer {

    @Override
    public String render(final MacroCallFtlElement macroCall) {
        return macroCall.getParameterStream()
                .map((entry) -> createFtlMacroParameter(entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(" ", "<@" + macroCall.getName() + " ", " />"));
    }

    private String createFtlMacroParameter(final String parameterName, final MacroCallParameterValue parameterValue) {
        final String stringValue;
        if (parameterValue instanceof MacroCallParameterStringValue) {
            stringValue = asString((MacroCallParameterStringValue) parameterValue);
        } else if (parameterValue instanceof MacroCallParameterMapValue) {
            stringValue = asString((MacroCallParameterMapValue) parameterValue);
        } else if (parameterValue instanceof MacroCallParameterBooleanValue) {
            return parameterName + "=" + ((MacroCallParameterBooleanValue) parameterValue).isValue();
        } else {
            throw new UnsupportedOperationException("Cannot create FTL Macro Parameter for parameter value class: "
                    + parameterValue.getClass());
        }

        return parameterName + "=\"" + stringValue.replaceAll("\"", "\\\\\"") + "\"";
    }

    private String asString(final MacroCallParameterStringValue parameterStringValue) {
        return parameterStringValue.getValue();
    }

    private String asString(final MacroCallParameterMapValue parameterMapValue) {
        Map<String, String> parameters = parameterMapValue.getValue();
        if (UtilValidate.isNotEmpty(parameters)) {
            return parameters.entrySet()
                    .stream()
                    .map(entry -> "'" + entry.getKey() + "':'" + entry.getValue() + "'")
                    .collect(Collectors.joining(",", "{", "}"));
        } else {
            return "";
        }
    }
}

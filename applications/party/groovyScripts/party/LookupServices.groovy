/*
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
 */

import org.apache.ofbiz.entity.condition.EntityCondition
import org.apache.ofbiz.entity.condition.EntityFieldValue
import org.apache.ofbiz.entity.condition.EntityFunction
import org.apache.ofbiz.entity.condition.EntityOperator
import org.apache.ofbiz.entity.util.EntityFindOptions

/*
 * lookupParty is deprecated, and only present for backward compatibility during the deprecation time
 */
def lookupParty() {
    Map serviceResult = run service: 'performFindParty', with: parameters
    List lookupResult = []
    serviceResult.listIt.getCompleteList().each {
        lookupResult << [label: it.firstName, value: it.partyId]
    }
    Map result = success()
    result.lookupResult = lookupResult
    return result
}

def lookupUserLogin() {
    def searchFieldsList = ["userLoginId", "partyId", "firstName", "lastName", "groupName"]
    def searchValue = "%" + parameters.searchTerm.toUpperCase() + "%"

    def orExprs = []
    searchFieldsList.each { fieldName ->
        orExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER(EntityFieldValue.makeFieldValue(fieldName)),
                EntityOperator.LIKE, searchValue))
    }

    def entityConditionList = EntityCondition.makeCondition(orExprs, EntityOperator.OR)

    EntityFindOptions findOptions = new EntityFindOptions()
    findOptions.setMaxRows(10)
    findOptions.setDistinct(true)

    def resultsList = autocompleteOptions = delegator.findList("UserLoginAndPartyDetails",
            entityConditionList,
            searchFieldsList.toSet(),
            searchFieldsList,
            findOptions,
            true)

    // For each result construct label and value properties which will be passed to the jquery autocompleter.
    // This should probably be done client-side, but there isn't an suitable hook to do so at the moment.
    def lookupResult = resultsList
            .collect {
                def item = ["value": it.userLoginId]
                def label = [it.partyId, it.firstName, it.lastName, "[$it.userLoginId]"]
                        .findAll()
                        .join(' ')
                item.put("label", label)

                item << it
                item
            }

    Map result = success()
    result.lookupResult = lookupResult

    return result
}
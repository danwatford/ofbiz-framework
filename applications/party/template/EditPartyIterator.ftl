<#list parties as party>
    ${setContextField("party", party)}
    ${setContextField("index", party?index)}
    <#if party.partyTypeId == "PERSON">
        ${screens.render("component://party/widget/partymgr/EditMultiplePartyNamesScreens.xml#EditPersonEmbeddedScreen")}
    <#else>
        ${screens.render("component://party/widget/partymgr/EditMultiplePartyNamesScreens.xml#EditGroupEmbeddedScreen")}
    </#if>
</#list>
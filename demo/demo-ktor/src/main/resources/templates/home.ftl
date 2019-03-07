<#-- @ftlvariable name="top" type="java.util.List<io.ktor.samples.kweet.model.Kweet>" -->
<#-- @ftlvariable name="latest" type="java.util.List<io.ktor.samples.kweet.model.Kweet>" -->

<#import "template.ftl" as layout />

<@layout.mainLayout title="${session.username}'s coffee maker">
    <form class="pure-form-stacked" action="/kettle/brew" method="post" enctype="application/x-www-form-urlencoded">
        <input class="pure-button pure-button-primary" type="submit" value="brew my coffee">
    </form>

    <#if coffee??>
        <p class="error">Coffee is ready !</p>
    </#if>

    <form class="pure-form-stacked" action="/login/clear" method="post" enctype="application/x-www-form-urlencoded">
        <input class="pure-button pure-button-primary" type="submit" value="logout">
    </form>
</@layout.mainLayout>
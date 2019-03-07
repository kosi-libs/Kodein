<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="userId" type="java.lang.String" -->

<#import "template.ftl" as layout />

<@layout.mainLayout title="Welcome">
<form class="pure-form-stacked" action="/login" method="post" enctype="application/x-www-form-urlencoded">
    <label for="userId">Username
        <input type="text" name="username" id="username" value="${username}">
    </label>

    <input class="pure-button pure-button-primary" type="submit" value="Login">
</form>
</@layout.mainLayout>

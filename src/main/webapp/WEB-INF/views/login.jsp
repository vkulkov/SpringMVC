<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<div>
    <h2>Sign in to Spitter</h2>

    <p>
        If you've been using Spitter from your phone,
        then that's amazing...we don't support IM yet.
    </p>

    <s:url value="/static/j_spring_security_check" var="authUrl"/>
    <form method="post" class="singin" action="${authUrl}">
        <fieldset>
            <table cellspacing="0">
                <tr>
                    <th><label for="username_or_email">Username or email</label></th>
                    <td><input id="username_or_email" name="j_username" type="text"/></td>
                </tr>
                <tr>
                    <th><label for="password">Password</label></th>
                    <td>
                        <input id="password" name="j_password" type="password"/>
                        <small><a href="/account/resend_password"/>Forgot?</small>
                    </td>
                </tr>
                <tr>
                    <th></th>
                    <td>
                        <input id="remember_me" name="j_spring_security_remember_me" type="checkbox"/>
                        <label for="remember_me" class="inline">Remember me</label>
                    </td>
                </tr>
                <tr>
                    <th></th>
                    <td><input name="commit" type="submit" value="Sign in"/></td>
                </tr>
            </table>
        </fieldset>
    </form>

    <script type="text/javascript">
        document.getElementById('username_or_email').focus();
    </script>
</div>
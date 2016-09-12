<div id="login" class="login">
    <ul id="loginnavbar" class="loginnavbar">
        <c:choose>
            <c:when test="${sessionScope.loggedUser!=null}">
                <li class="loginnavbarItem"><a href="/userProfile">${sessionScope.loggedUser.userName}</a></li>
                <li class="loginnavbarItem"><a href="/logout">Signout</a></li>
            </c:when>
            <c:otherwise>
                <li class="loginnavbarItem"><a href="/login">Login</a></li>
                <li class="loginnavbarItem"><a href="/signup">Signup</a></li>
            </c:otherwise>
        </c:choose>
    </ul>
</div>
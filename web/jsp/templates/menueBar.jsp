<div id="logo" class="logo">
    <img alt="" src="/resources/images/images1.png" height="">
</div>
<div id="menu" class="menu">
    <ul id="navbar" class="navbar">
        <li class="navbarItem"><a href="/main">HOME</a></li>
        <li class="navbarItem"><a href="#">MORE</a></li>
        <li class="navbarItem"><a href="/faq">FAQ</a></li>
        <li class="navbarItem"><a href="#">ABOUT US</a></li>
        <c:if test="${sessionScope.loggedUser.roles.contains('admin') || sessionScope.loggedUser.roles.contains('manager') || sessionScope.loggedUser.roles.contains('staff')}">
            <li class="navbarItem"><a href="/admin/employee">ADMIN</a></li>
        </c:if>
    </ul>
</div>
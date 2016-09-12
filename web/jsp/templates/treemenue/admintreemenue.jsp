<div class="administrationTreeMenue" id="administrationTreeMenue">
    <ul>
        <c:if test="${sessionScope.loggedUser.roles.contains('admin') || sessionScope.loggedUser.roles.contains('manager')}">
            <li><a>Employee</a></li>
            <ul>
                <li><a href="/admin/employee">Create Employee</a></li>
                <c:if test="${sessionScope.loggedUser.roles.contains('admin')}">
                    <li><a href="/admin/manageEmployee">Manage Employee</a></li>
                </c:if>
            </ul>
        </c:if>


        <li><a>Ticket Cancellation</a></li>
        <ul>
            <li><a href="/admin/ticketCancellation">Cancellation Requests</a></li>
        </ul>
        <!--
        <li><a>Reports</a></li>
        <ul>
            <li><a href="#">Employees</a></li>
            <li><a href="#">Reservations</a></li>
            <li><a href="#">Bookings</a></li>
        </ul>
        -->
    </ul>
</div>
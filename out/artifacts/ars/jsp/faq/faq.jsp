<%@ page import="java.util.List" %>
<%@ page import="com.ars.domain.faq.FAQQuestion" %>
<%@ page import="com.ars.system.APPStatics" %>
<%--
  Created by IntelliJ IDEA.
  User: JJ
  Date: 5/3/16
  Time: 2:37 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    List<FAQQuestion> faqQuestions = (List<FAQQuestion>)request.getAttribute(APPStatics.RequestStatics.FAQS);
    pageContext.setAttribute("faqQuestionsJstl", faqQuestions);
%>

<html>
<head>
    <title>ARS | FAQ</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/styles.css">
    <link rel="stylesheet" href="/resources/javascript/jquery-ui.min.css">
    <script src="/resources/javascript/jquery.js"></script>
    <script src="/resources/javascript/jquery-ui.min.js"></script>

    <script>
        $(document).ready(function(){
            var numberOfRecords = parseInt($("#numberOfRecords").val());
            var recordsPerPage = parseInt($("#recordsPerPageDropDown").val());
            var totalPage = Math.ceil(numberOfRecords/recordsPerPage);
            var pageNumber = parseInt($("#pageNumber>input").val());
            var form = $("#page-navigation");
            //alert(numberOfRecords +","+recordsPerPage+","+totalPage);
            $("#pageNumber>label").html(totalPage);

            $("#nextPage").click(function(){
                pageNumber = parseInt($("#pageNumber>input").val());
                if(pageNumber<totalPage){
                    pageNumber = pageNumber+1;
                    $("#pageNumber>input").val(pageNumber);
                    form.submit();
                    //$("#pageNumber>input").val(pageNumber);
                }
            });
            $("#previousPage").click(function(){
                pageNumber = parseInt($("#pageNumber>input").val());
                if(pageNumber>1){
                    pageNumber = pageNumber-1;
                    $("#pageNumber>input").val(pageNumber);
                    form.submit();
                    //$("#pageNumber>input").val(pageNumber);
                }
            });
            $("#lastPage").click(function(){
                pageNumber = parseInt(totalPage);
                $("#pageNumber>input").val(pageNumber);
                form.submit();
            });
            $("#firstPage").click(function(){
                pageNumber = 1;
                $("#pageNumber>input").val(pageNumber);
                form.submit();
            });
            $("#recordsPerPage>select").change(function(){
                pageNumber = 1;
                recordsPerPage = $(this).val();
                $("#pageNumber>input").val(pageNumber);
                form.submit();
            });
            $("#pageNumber>input").keypress(function(event){
                if(event.which == 13){
                    pageNumber = parseInt($("#pageNumber>input").val()) || pageNumber;
                    if(pageNumber>totalPage){
                        pageNumber=parseInt(totalPage);
                    }else if(pageNumber<1){
                        pageNumber=1;
                    }
                    $("#pageNumber>input").val(pageNumber);
                    form.submit();
                }
            });
        });

        function displayAnswerArea(tag){
            var tagID = ($(tag).attr('id'));
            $("#"+tagID + "AnswerForm").css('display','block');
        }

    </script>

</head>
<body>

    <div id="topbar" class="topbar">
        <%@include file="../templates/menueBar.jsp" %>
        <%@include file="../templates/userLoginInfo.jsp" %>
    </div>



    <div class="faqContentPane contentPane" id="contentPane">
        <div class="faqsearch">
            <form method="post" action="/faq">
                <div class="searchbutton"><input type="submit" value="Search" name="newFAQSearch" class="button-default"/></div>
                <div class="searchinput"><input type="text" name="searchText" value="${requestScope.searchText}"/></div>
                <input type="hidden" name="pageNumber" value="1"/>
                <input type="hidden" name="recordsPerPage" value="2"/>
            </form>
        </div>

        <div id="faqFormContainer" class="faqFormContainer">

            <div class="faqMessageSection">
                <c:if test="${requestScope.successMessage!=null}">
                    <div class="successMessage">
                        <p>${requestScope.successMessage}</p>
                    </div>
                </c:if>
                <c:if test="${requestScope.errorMessage!=null}">
                    <div class="errorMessage">
                        <p>${requestScope.errorMessage}</p>
                    </div>
                </c:if>
                <c:if test="${requestScope.informationMessage!=null}">
                    <div class="informationMessage">
                        <p>${requestScope.informationMessage}</p>
                    </div>
                </c:if>
            </div>

            <div class="header">
                <p>Frequently Asked Questions</p>
            </div>


            <div class="questionAnswers">
                <c:forEach items="${faqQuestionsJstl}" var="question">
                    <div class="questionDiv">
                        <div class="questionID">Q : ${question.questionId}</div>
                        <div class="question">${question.question}</div>
                        <div class="userQuestion">${question.user.userName}</div>
                        <div class="dateQuestion">${question.date}</div>
                        <div class="timeQuestion">${question.time}</div>
                        <c:if test="${sessionScope.loggedUser.roles.contains('admin') || sessionScope.loggedUser.roles.contains('manager') || sessionScope.loggedUser.roles.contains('staff')}">
                            <div class="answerButton">
                                <input type="button" name="postAnswer" class="postAnswer" id="${question.questionId}" value="Answer to this question" onclick="displayAnswerArea(this);"/>
                            </div>
                            <form method="post" name="${question.questionId}TextArea" class="answerForm" id="${question.questionId}AnswerForm" action="/faq" style="display: none">
                                <div class="newAnswer">
                                    <textarea name="newAnswerTextArea" class="newAnswerTextArea" id="${question.questionId}TextArea" placeholder="Write answer for this question" rows="4" ></textarea>
                                </div>
                                <div class="submitAnswerButton">
                                    <input type="text" hidden="true" name="questionID" value="${question.questionId}"/>
                                    <input type="submit" name="submitAnswer" class="submitAnswer" id="${question.questionId}Submit" value="Submit Answer"/>
                                </div>
                            </form>
                        </c:if>
                    </div>
                    <c:forEach items="${question.faqAnswers}" var="answer">
                        <div class="answerDiv">
                            <div class="answerID">RE : ${answer.answerId}</div>
                            <div class="answer">${answer.answer}</div>
                            <div class="userAnswer">${answer.user.userName}</div>
                            <div class="dateAnswer">${answer.date}</div>
                            <div class="timeAnswer">${answer.time}</div>
                        </div>
                    </c:forEach>
                </c:forEach>
            </div>

            <div class="faqpagenavigationpannel">
                <form method="post" action="/faq" id="page-navigation">
                    <input type="hidden" name="numberOfRecords" id="numberOfRecords" value="${requestScope.numberOfRecords}"/>
                    <input type="hidden" name="searchText" id="searchText" value="${requestScope.searchText}"/>
                    <ul class="pagination-navigation">
                        <li><a class="firstPage" id="firstPage"></a></li>
                        <li><a class="previousPage" id="previousPage"></a></li>
                        <li><a class="pageNumber" id="pageNumber"><input type="text" value="${requestScope.pageNumber}" name="pageNumber"> of <label></label></a></li>
                        <li><a class="nextPage" id="nextPage"></a></li>
                        <li><a class="lastPage" id="lastPage"></a></li>
                        <li><a class="recordsPerPage" id="recordsPerPage">
                            <select name="recordsPerPage" id="recordsPerPageDropDown">
                                <option value="2" <c:if test="${requestScope.recordsPerPage=='2'}">selected</c:if>>2</option>
                                <option value="5" <c:if test="${requestScope.recordsPerPage=='5'}">selected</c:if>>5</option>
                                <option value="8" <c:if test="${requestScope.recordsPerPage=='8'}">selected</c:if>>8</option>
                                <option value="15" <c:if test="${requestScope.recordsPerPage=='15'}">selected</c:if>>15</option>
                            </select>
                        </a></li>
                    </ul>
                </form>
            </div>

            <c:if test="${sessionScope.loggedUser!=null && sessionScope.loggedUser.mailConfirmed}">
                <form id="faq" class="faq" method="post" action="/faq">
                    <div class="inputs">
                        <div id="line1">
                            <textarea name="question" placeholder="Place your question here" rows="4" autofocus></textarea>
                        </div>
                        <div id="line2">
                            <input type="submit" class="submit" id="submitQuestion" name="submitQuestion" value="Submit Question"/>
                        </div>
                    </div>
                </form>
            </c:if>
        </div>



    </div>

    <div class="footer">
        <%@include file="../templates/footer.jsp" %>
    </div>

</body>
</html>

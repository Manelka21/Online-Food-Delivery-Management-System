<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<aside class="w-64 bg-yellow-400 border-r border-gray-200 flex flex-col h-screen p-6">
  <a href="${pageContext.request.contextPath}/driver/dashboard" class="flex items-center gap-4 mb-8 text-black group">
    <div class="w-15 h-15 mx-auto bg-white rounded-full flex items-center justify-center text-[40px] group-hover:bg-blue-50 transition">🚴</div>
    <div>
      <div class="font-extrabold text-[30px] tracking-tight">FoodFleet</div>
      <div class="text-lg font-bold text-black">Driver Portal</div>
    </div>
  </a>

  <span class="text-lg font-bold text-black-600 uppercase tracking-wider mb-4">Navigation</span>

  <nav class="space-y-1 flex-1">
    <a href="${pageContext.request.contextPath}/driver/dashboard"
       class="flex items-center gap-3 px-4 py-3 rounded-2xl text-lg font-bold transition ${currentPage == 'dashboard' ? 'bg-black text-white' : 'text-gray-900 hover:bg-gray-50'}">
      <span class="text-2xl">📊</span> Dashboard
    </a>

    <a href="${pageContext.request.contextPath}/driver/profile"
       class="flex items-center gap-3 px-4 py-3 rounded-2xl text-lg font-bold transition ${currentPage == 'profile' ? 'bg-black text-white' : 'text-gray-900 hover:bg-gray-50'}">
      <span class="text-2xl">👤</span> My Profile
    </a>

    <a href="${pageContext.request.contextPath}/driver/edit"
       class="flex items-center gap-3 px-4 py-3 rounded-2xl text-lg font-bold transition ${currentPage == 'edit' ? 'bg-black text-white' : 'text-gray-900 hover:bg-gray-50'}">
      <span class="text-2xl">✏️</span> Edit Details
    </a>

    <span class="block pt-6 pb-2 text-lg font-bold text-black-600 uppercase tracking-wider">Admin</span>

    <a href="${pageContext.request.contextPath}/driver/all"
       class="flex items-center gap-3 px-4 py-3 rounded-2xl text-lg font-bold transition ${currentPage == 'all' ? 'bg-green-500 text-white' : 'text-gray-900 hover:bg-gray-50'}">
      <span class="text-2xl">📋</span> All Drivers
    </a>
  </nav>

  <div class="pt-6 border-t border-gray-200 space-y-4">
    <c:if test="${not empty sessionScope.loggedInDriver}">
      <div class="flex items-center gap-3 p-2 bg-gray-50 rounded-2xl">
        <div class="w-10 h-10 flex items-center justify-center bg-gray-200 rounded-full font-extrabold text-lg text-black uppercase">
          ${sessionScope.loggedInDriver.firstName.substring(0,1)}${sessionScope.loggedInDriver.lastName.substring(0,1)}
        </div>
        <div class="min-w-0 flex-1">
          <div class="text-lg font-extrabold text-gray-900 truncate">
            ${sessionScope.loggedInDriver.firstName} ${sessionScope.loggedInDriver.lastName}
          </div>
          <div class="text-[15px] font-bold text-gray-400 truncate">${sessionScope.loggedInDriver.driverId}</div>
        </div>
      </div>
    </c:if>
    <a href="${pageContext.request.contextPath}/driver/logout" class="flex items-center justify-center gap-2 w-full px-4 py-3 bg-gray-100 hover:bg-red-50 text-gray-700 hover:text-red-600 text-xl font-bold rounded-2xl transition">
      🔓 Sign Out
    </a>
  </div>
</aside>
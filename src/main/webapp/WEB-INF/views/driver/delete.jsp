<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Dashboard – FoodFleet Driver</title>
  <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 min-h-screen font-sans">
<c:set var="currentPage" value="dashboard"/>

<div class="flex h-screen overflow-hidden">
  <%@ include file="sidebar.jsp" %>

  <main class="flex-1 overflow-y-auto p-10">
    <div class="mb-10">
      <p class="text-xs text-gray-400 mb-2">Home</p>
      <h1 class="text-4xl font-extrabold text-black tracking-tight">Welcome back, ${driver.firstName}! 👋</h1>
      <p class="text-gray-500 mt-1">Here's an overview of your driver account.</p>
    </div>

    <c:if test="${not empty success}">
      <div class="p-4 bg-green-50 border border-green-200 rounded-2xl text-green-600 text-xs mb-6">✅ ${success}</div>
    </c:if>
    <c:if test="${not empty error}">
      <div class="p-4 bg-red-50 border border-red-200 rounded-2xl text-red-600 text-xs mb-6">⚠️ ${error}</div>
    </c:if>

    <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-10">
      <div class="bg-black text-white rounded-3xl p-6 flex flex-col justify-between h-36">
        <span class="text-xs font-bold tracking-wider text-gray-400 uppercase">Driver ID</span>
        <span class="text-2xl font-black">${driver.driverId}</span>
      </div>

      <div class="bg-white border border-gray-200 rounded-3xl p-6 flex flex-col justify-between h-36 shadow-sm">
        <span class="text-xs font-bold tracking-wider text-gray-400 uppercase">Status</span>
        <span class="text-sm">
          <c:choose>
            <c:when test="${driver.status == 'ACTIVE'}">
              <span class="bg-green-50 text-green-600 font-semibold px-3 py-1 rounded-full text-xs">● Active</span>
            </c:when>
            <c:when test="${driver.status == 'ON_DELIVERY'}">
              <span class="bg-orange-50 text-orange-600 font-semibold px-3 py-1 rounded-full text-xs">● On Delivery</span>
            </c:when>
            <c:otherwise>
              <span class="bg-gray-100 text-gray-500 font-semibold px-3 py-1 rounded-full text-xs">● Inactive</span>
            </c:otherwise>
          </c:choose>
        </span>
      </div>

      <div class="bg-white border border-gray-200 rounded-3xl p-6 flex flex-col justify-between h-36 shadow-sm">
        <span class="text-xs font-bold tracking-wider text-gray-400 uppercase">Vehicle</span>
        <span class="text-sm font-extrabold text-gray-900">
          <c:choose>
            <c:when test="${driver.vehicleType == 'BIKE'}">🚲 Bicycle</c:when>
            <c:when test="${driver.vehicleType == 'SCOOTER'}">🛵 Scooter</c:when>
            <c:when test="${driver.vehicleType == 'MOTORBIKE'}">🏍️ Motorbike</c:when>
            <c:when test="${driver.vehicleType == 'CAR'}">🚗 Car</c:when>
            <c:otherwise>${driver.vehicleType}</c:otherwise>
          </c:choose>
        </span>
      </div>

      <div class="bg-white border border-gray-200 rounded-3xl p-6 flex flex-col justify-between h-36 shadow-sm">
        <span class="text-xs font-bold tracking-wider text-gray-400 uppercase">Member Since</span>
        <span class="text-sm font-extrabold text-gray-900">${driver.registeredDate}</span>
      </div>
    </div>

    <div class="bg-white border border-gray-200 rounded-3xl p-8 mb-8 shadow-sm">
      <h2 class="text-xl font-bold text-black mb-6">Quick Actions</h2>
      <div class="flex flex-wrap gap-4">
        <a href="${pageContext.request.contextPath}/driver/profile" class="bg-gray-100 hover:bg-gray-200 text-gray-900 font-bold px-6 py-3 rounded-full text-xs transition">
          👤 View Profile
        </a>
        <a href="${pageContext.request.contextPath}/driver/edit" class="bg-black hover:bg-blue-600 text-white font-bold px-6 py-3 rounded-full text-xs transition">
          ✏️ Edit Details
        </a>
        <a href="${pageContext.request.contextPath}/driver/all" class="bg-gray-100 hover:bg-gray-200 text-gray-900 font-bold px-6 py-3 rounded-full text-xs transition">
          📋 All Drivers
        </a>
        <a href="${pageContext.request.contextPath}/driver/delete" class="bg-red-50 hover:bg-red-100 text-red-600 font-bold px-6 py-3 rounded-full text-xs transition">
          🗑️ Delete Account
        </a>
      </div>
    </div>

    <div class="bg-white border border-gray-200 rounded-3xl p-8 shadow-sm">
      <h2 class="text-base font-bold text-gray-400 uppercase tracking-wider mb-6">Account Summary</h2>
      <div class="py-4 border-b border-gray-100 flex justify-between text-sm">
        <span class="font-bold text-gray-400">Full Name</span>
        <span class="font-extrabold text-gray-900">${driver.firstName} ${driver.lastName}</span>
      </div>
      <div class="py-4 border-b border-gray-100 flex justify-between text-sm">
        <span class="font-bold text-gray-400">Email</span>
        <span class="font-semibold text-gray-900">${driver.email}</span>
      </div>
      <div class="py-4 border-b border-gray-100 flex justify-between text-sm">
        <span class="font-bold text-gray-400">Phone</span>
        <span class="font-semibold text-gray-900">${driver.phone}</span>
      </div>
      <div class="py-4 flex justify-between text-sm">
        <span class="font-bold text-gray-400">License No.</span>
        <span class="font-semibold text-gray-900">${driver.licenseNumber}</span>
      </div>
    </div>
  </main>
</div>
</body>
</html>